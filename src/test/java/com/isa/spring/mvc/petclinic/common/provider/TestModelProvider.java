package com.isa.spring.mvc.petclinic.common.provider;

public interface TestModelProvider<T> {
    default T randomModel(){
        return sameModel();
    }

    T sameModel();
}
