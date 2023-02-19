package com.f1uctus.bloom.application.controllers.workflows;

import com.f1uctus.bloom.application.common.controls.DelegatingTreeCell;
import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.application.controllers.workflows.actions.WorkflowTreeCell;
import com.f1uctus.bloom.application.controllers.workflows.triggers.TriggerTreeCell;
import com.f1uctus.bloom.core.BloomPluginHost;
import com.f1uctus.bloom.core.persistence.models.*;
import com.f1uctus.bloom.core.persistence.repositories.WorkflowRepository;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class WorkflowsController extends ReactiveController {
    @FXML TreeView<PropertiedEntity<?>> tree;

    final WorkflowRepository workflows;
    final PlatformTransactionManager txManager;
    final BloomPluginHost host;

    TransactionTemplate tx;

    @Setter User user;

    @Override
    public void initialize() {
        tx = new TransactionTemplate(txManager);
        user = (User) host.getIdentityHolder();

        tree.setEditable(true);
        tree.setCellFactory(tv -> new DelegatingTreeCell<>(Map.of(
            Workflow.class, () -> new WorkflowTreeCell(context),
            Trigger.class, () -> new TriggerTreeCell(getBeansOfType(EventPlugin.class).stream()
                .map(ep -> ((EventPlugin<?>) ep)).collect(toList()))
        )));
        tree.setShowRoot(false);
        var root = new TreeItem<PropertiedEntity<?>>();
        root.setExpanded(true);
        tree.setRoot(root);
    }

    @Override
    public void afterSetup() {
        getStage().setOnCloseRequest(this::onClose);
        // Load workflow tree
        loadToTree(workflows.findByUser(user));
    }

    private List<Workflow> loadFromTree() {
        return tree.getRoot()
            .getChildren()
            .stream()
            .map(TreeItem::getValue)
            .map(v -> (Workflow) v)
            .collect(toList());
    }

    private void loadToTree(List<Workflow> wfs) {
        tree.getRoot().getChildren().setAll(wfs.stream().map(this::toTreeItem).toList());
    }

    private TreeItem<PropertiedEntity<?>> toTreeItem(Workflow wf) {
        var ti = new TreeItem<PropertiedEntity<?>>(wf);
        ti.getChildren()
            .addAll(wf.getTriggers().stream().map(TreeItem<PropertiedEntity<?>>::new).toList());
        return ti;
    }

    public void onSaveAction(ActionEvent event) {
        tx.execute(s -> {
            workflows.deleteByUser(user);
            loadToTree(workflows.saveAll(loadFromTree()));
            return null;
        });
    }

    public void onAddAction(ActionEvent event) {
        var item = tree.getSelectionModel().getSelectedItem();
        if (item == null) {
            tree.getRoot().getChildren().add(new TreeItem<>(new Workflow(user, "New workflow")));
        } else if (item.getValue() instanceof Workflow w) {
            var t = new Trigger(user);
            w.getTriggers().add(t);
            item.getChildren().add(new TreeItem<>(t));
        } else if (item.getParent().getValue() instanceof Workflow w) {
            var t = new Trigger(user);
            w.getTriggers().add(t);
            item.getParent().getChildren().add(new TreeItem<>(t));
        }
    }

    public void onRemoveAction(ActionEvent event) {
        var item = tree.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        if (item.getValue() instanceof Trigger t && item.getParent()
            .getValue() instanceof Workflow w) {
            w.getTriggers().remove(t);
        }
        item.getParent().getChildren().remove(item);
    }

    public void onClose(WindowEvent event) {
        var wfs = loadFromTree();
        if (wfs.stream().anyMatch(Workflow::isChanged)) {
            new Alert(
                Alert.AlertType.WARNING,
                "You have unsaved changes. Do you want to apply them?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
            ).showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    tx.execute(s -> {
                        workflows.deleteByUser(user);
                        workflows.saveAll(wfs);
                        return null;
                    });
                } else if (type == ButtonType.CANCEL) {
                    event.consume();
                }
            });
        }
    }

    @Override
    public Stage getStage() {
        return (Stage) tree.getScene().getWindow();
    }
}
