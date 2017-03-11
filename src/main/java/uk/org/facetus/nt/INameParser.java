/*
 * Copyright 2017 Simon Bssutler.
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

import java.util.List;

/**
 * Basic services to be implemented by name parsers.
 * 
 */
public interface INameParser {

    /**
     * Parses a name and returns a parse tree in Penn Treebank format, 
     * {e.g.} (S (NP (NNP Romeo)) (VP (VBZ loves) (NNP Juliet))).
     * @param tokens a list of identifier name tokens
     * @return a Penn Treebank parse tree as a string
     */    
    public String parseTree(final List<String> tokens);
      
    /**
     * Parses a name and returns a phrasal summary of the parse tree as one or 
     * more phrases, {e.g.} (S (NP (NNP Romeo)) (VP (VBZ loves) (NNP Juliet))) 
     * is summarised as NP VP.
     * 
     * @param tokens a list of identifier name tokens 
     * 
     * @return A string containing phrasal tags.
     */
    public String summarise(final List<String> tokens);
}
