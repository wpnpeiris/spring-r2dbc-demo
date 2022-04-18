/*
 * This file is generated by jOOQ.
 */
package com.sithroo.r2dbc.demo.db.generated;


import com.sithroo.r2dbc.demo.db.generated.tables.DemoEntry;
import com.sithroo.r2dbc.demo.db.generated.tables.records.DemoEntryRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * the default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DemoEntryRecord> DEMO_ENTRY_PKEY = Internal.createUniqueKey(DemoEntry.DEMO_ENTRY, DSL.name("demo_entry_pkey"), new TableField[] { DemoEntry.DEMO_ENTRY.ID }, true);
}
