// since v1.0.0
// created: 15.11.2014
// revision: 1.0
// author: Ilya Gubarev

package com.soulwarelabs.jbrandy.core;

import java.io.Writer;

public class PresenterImplementation implements Presenter {

    public static class Builder {

        private Builder() {

        }

        public PresenterImplementation build() {
            return new PresenterImplementation(this);
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private PresenterImplementation(Builder builder) {

    }

    public Writer apply(Writer output, Object model) {
        throw new UnsupportedOperationException();
    }
}
