@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class)
})
package com.f1uctus.bloom.core.persistence;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;