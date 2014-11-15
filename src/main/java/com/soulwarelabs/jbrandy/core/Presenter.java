// since v1.0.0
// created: 15.11.2014
// revision: 1.0
// author: Ilya Gubarev

package com.soulwarelabs.jbrandy.core;

import java.io.Writer;

public interface Presenter {

    Writer apply(Writer output, Object model);
}
