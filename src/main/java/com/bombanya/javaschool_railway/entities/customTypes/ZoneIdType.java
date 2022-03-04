package com.bombanya.javaschool_railway.entities.customTypes;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import java.time.ZoneId;

public class ZoneIdType
        extends AbstractSingleColumnStandardBasicType<ZoneId>
        implements DiscriminatorType<ZoneId> {

    public static final ZoneIdType INSTANCE = new ZoneIdType();

    public ZoneIdType(){
        super(VarcharTypeDescriptor.INSTANCE, ZoneIdTypeDescriptor.INSTANCE);
    }

    @Override
    public ZoneId stringToObject(String s) throws Exception {
        return fromString(s);
    }

    @Override
    public String objectToSQLString(ZoneId zoneId, Dialect dialect) throws Exception {
        return toString(zoneId);
    }

    @Override
    public String getName() {
        return "zoneid";
    }
}
