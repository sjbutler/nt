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
 * A factory to create identifier name taggers.
 * 
 */
public class NameTaggerFactory {
    private static final Logger LOGGER = 
            LoggerFactory.getLogger( NameTaggerFactory.class );
    
    private static NameTagger classTagger;
    private static NameTagger referenceTagger;
    
    private static NameTaggerFactory instance;
    
    /**
     * Retrieve the instance of the factory.
     * @return the factory
     */
    public static NameTaggerFactory getInstance() {
        if (instance == null) {
            instance = new NameTaggerFactory();
        }
        
        return instance;
    }
    
    private NameTaggerFactory() {}
    
    /**
     * Creates a name tagger of the specified type.
     * 
     * @param tt the type of name tagger required
     * @return a name tagger of the specified type
     */
    public NameTagger create(PosTaggerType tt) {
        
        switch (tt) {
            case CLASS:
                if (classTagger == null ) {
                    classTagger = new ClassNameTagger(
                            "/models/en-class-pos-maxent.bin");
                }
                return classTagger;
                    
            case REFERENCE:
                if (referenceTagger == null) {
                    referenceTagger = new ReferenceNameTagger(
                            "/models/en-reference-pos-maxent.bin");
                }
                return referenceTagger;
                
            default:
                LOGGER.error( "Unrecognised tagger type passed to the name tagger factory" );
                throw new IllegalStateException("Unrecognised tagger type passed to factory ");
        }
    }
}
