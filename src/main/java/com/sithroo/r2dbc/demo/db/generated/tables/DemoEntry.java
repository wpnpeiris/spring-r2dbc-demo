/*
 * This file is generated by jOOQ.
 */
package com.sithroo.r2dbc.demo.db.generated.tables;


import com.sithroo.r2dbc.demo.db.generated.DefaultSchema;
import com.sithroo.r2dbc.demo.db.generated.Keys;
import com.sithroo.r2dbc.demo.db.generated.tables.records.DemoEntryRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DemoEntry extends TableImpl<DemoEntryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>demo_entry</code>
     */
    public static final DemoEntry DEMO_ENTRY = new DemoEntry();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DemoEntryRecord> getRecordType() {
        return DemoEntryRecord.class;
    }

    /**
     * The column <code>demo_entry.id</code>.
     */
    public final TableField<DemoEntryRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>demo_entry.entry</code>.
     */
    public final TableField<DemoEntryRecord, String> ENTRY = createField(DSL.name("entry"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>demo_entry.created</code>.
     */
    public final TableField<DemoEntryRecord, LocalDateTime> CREATED = createField(DSL.name("created"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.LOCALDATETIME)), this, "");

    private DemoEntry(Name alias, Table<DemoEntryRecord> aliased) {
        this(alias, aliased, null);
    }

    private DemoEntry(Name alias, Table<DemoEntryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>demo_entry</code> table reference
     */
    public DemoEntry(String alias) {
        this(DSL.name(alias), DEMO_ENTRY);
    }

    /**
     * Create an aliased <code>demo_entry</code> table reference
     */
    public DemoEntry(Name alias) {
        this(alias, DEMO_ENTRY);
    }

    /**
     * Create a <code>demo_entry</code> table reference
     */
    public DemoEntry() {
        this(DSL.name("demo_entry"), null);
    }

    public <O extends Record> DemoEntry(Table<O> child, ForeignKey<O, DemoEntryRecord> key) {
        super(child, key, DEMO_ENTRY);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<DemoEntryRecord, Long> getIdentity() {
        return (Identity<DemoEntryRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<DemoEntryRecord> getPrimaryKey() {
        return Keys.DEMO_ENTRY_PKEY;
    }

    @Override
    public List<UniqueKey<DemoEntryRecord>> getKeys() {
        return Arrays.<UniqueKey<DemoEntryRecord>>asList(Keys.DEMO_ENTRY_PKEY);
    }

    @Override
    public DemoEntry as(String alias) {
        return new DemoEntry(DSL.name(alias), this);
    }

    @Override
    public DemoEntry as(Name alias) {
        return new DemoEntry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DemoEntry rename(String name) {
        return new DemoEntry(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DemoEntry rename(Name name) {
        return new DemoEntry(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
