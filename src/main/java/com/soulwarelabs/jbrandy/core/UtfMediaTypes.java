/*
 * Project:  jBrandy Core
 * Outline:  jBrandy framework core components
 *
 * File:     UtfMediaType.java
 * Folder:   src/main/java/com/soulwarelabs/jbrandy/core
 * Revision: 1.01, 30 November 2014
 * Created:  29 November 2014
 * Authors:  Ilya Gubarev
 *
 * Copyright (c) 2014 Soulware Labs, Ltd.
 * Contact information is available at "http://www.soulwarelabs.com".
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       "http://www.apache.org/licenses/LICENSE-2.0".
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soulwarelabs.jbrandy.core;

/**
 * Media types MIME types with specified UTF-8 encoding.
 *
 * @since v1.0.0
 *
 * @author Ilya Gubarev
 * @version 30 November 2014
 */
public final class UtfMediaTypes {

    /**
     * HTML content.
     *
     * @since v1.0.0
     */
    public static final String TEXT_HTML = "text/html;charset=utf-8";

    /**
     * Plain text.
     *
     * @since v1.0.0
     */
    public static final String TEXT_PLAIN = "text/plain;charset=utf-8";

    private UtfMediaTypes() {

    }
}
