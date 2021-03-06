/*
 * Copyright 2017 .
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * 
 */
public class NameTaggerTest {
    @Test
    @Ignore
    public void simpleTaggerTest() {
        INameTagger tagger = NameTaggerFactory.getInstance().create( PosTaggerType.REFERENCE );
        
        List<String> tokens = new ArrayList<>();
        
        tokens.add( "Romeo");
        tokens.add( "loves");
        tokens.add( "Juliet");
        
        System.out.println( tagger.tag( tokens ).stream().map( t -> t.normalisedToken() + "_" + t.tag() ).collect( Collectors.joining( " ")) );
    }
}
