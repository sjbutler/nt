/*
 * Copyright 2017 Simon Butler.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.facetus.nt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a factory for creating name parsers.
 * 
 */
public class NameParserFactory {
    private static final Logger LOGGER = 
            LoggerFactory.getLogger( NameParserFactory.class );
    private static INameParser classParser;
    private static INameParser referenceParser;
    
    private static NameParserFactory instance;
    
    /**
     * Retrieve the instance of the factory.
     * @return the factory
     */
    public static NameParserFactory getInstance() {
        if (instance == null) {
            instance = new NameParserFactory();
        }
        
        return instance;
    }
    
    private NameParserFactory() {}
    
    /**
     * Creates a parser of the specified type.
     * 
     * @param taggerType the type of name tagger required
     * @return a name parser of the specified type
     */
    public INameParser create(PosTaggerType taggerType) {
        
        switch (taggerType) {
            case CLASS:
                if (classParser == null ) {
                    classParser = new NameParser(taggerType);
                }
                return classParser;
                    
            case REFERENCE:
                if (referenceParser == null) {
                    referenceParser = new NameParser(taggerType);
                }
                return referenceParser;
                
            default:
                LOGGER.error("Unrecognised tagger type passed to parser factory");
                throw new IllegalStateException(
                        "Unrecognised tagger type passed to parser factory ");
        }
    }
}
