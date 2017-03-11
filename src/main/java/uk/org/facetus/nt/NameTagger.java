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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PoS tagger for identifier names.
 * 
 */
public class NameTagger implements INameTagger {
    
    private static final Logger LOGGER
            = LoggerFactory.getLogger( NameTagger.class );
    
    private POSModel model;
    private POSTaggerME tagger;
    
    NameTagger(String modelPath) {
        try (InputStream is = this.getClass().getResourceAsStream( modelPath ) ) {
            model = new POSModel(is);
            tagger = new POSTaggerME(model);
        }
        catch(FileNotFoundException e) {
            LOGGER.error( "Unable to locate PoS tagger model at {0}", modelPath, e );
            throw new IllegalStateException("PoS Tagger model not found.", e);
        }
        catch(IOException e) {
            LOGGER.error( "Encountered problem reading PoS tagger model at {0}", modelPath, e );
            throw new IllegalStateException( 
                    String.format( "Unable to read PoS tagger model at \"%s\"", modelPath), 
                    e);
        }
    }
    
    @Override
    public List<PosTaggedToken> tag(List<String> tokens) {
        String[] tags = this.tagger.tag( tokens.toArray( new String[0] ) );
        
        List<PosTaggedToken> output = new ArrayList<>();
        
        for (int i = 0; i < tokens.size(); i++) {
            output.add(new PosTaggedToken( tokens.get( i ), tags[i] ));
        }
        
        return output;
    }
    
    POSModel model() {
        return this.model;
    }
}
