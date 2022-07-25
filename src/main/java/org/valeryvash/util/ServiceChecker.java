package org.valeryvash.util;

//TODO add check methods for every entity
public class ServiceChecker {

    public static void throwIfNull(Object object) {
        if (object == null) {
            throw new NullPointerException("null observed in Service layer");
        }
    }

}
