package com.bombanya.javaschool_railway.entities.customTypes;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.time.ZoneId;

public class ZoneIdTypeDescriptor extends AbstractTypeDescriptor<ZoneId> {

    public static final ZoneIdTypeDescriptor INSTANCE = new ZoneIdTypeDescriptor();

    public ZoneIdTypeDescriptor(){
        super(ZoneId.class);
    }

    @Override
    public String toString(ZoneId value) {
        return value.toString();
    }

    @Override
    public ZoneId fromString(String s) {
        if (s == null || s.isEmpty()) return null;
        return ZoneId.of(s);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <X> X unwrap(ZoneId zoneId, Class<X> aClass, WrapperOptions wrapperOptions) {
        if (zoneId == null) return null;
        if (ZoneId.class.isAssignableFrom(aClass)) return (X) zoneId;
        if (String.class.isAssignableFrom(aClass)) return (X) toString(zoneId);
        throw unknownUnwrap(aClass);
    }

    @Override
    public <X> ZoneId wrap(X x, WrapperOptions wrapperOptions) {
        if (x == null) return null;
        if (x instanceof String) return fromString((String) x);
        if (x instanceof ZoneId) return (ZoneId) x;
        throw unknownWrap(x.getClass());
    }
}
