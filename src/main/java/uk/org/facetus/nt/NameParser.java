/*
 * Copyright 2017 Simon Butler
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a parser for identifier names. Do not instantiate this class
 * directly, use the {@link NameParserFactory} to create the required parser. 
 * 
 */
public class NameParser implements INameParser {
    private static final Logger LOGGER
            = LoggerFactory.getLogger( NameTagger.class );
    private static final String PARSER_MODEL_PATH 
            = "/parsers/en-parser-chunking.bin";
    private final ParserModel parserModel;
    
    private final NameTagger tagger;
    
    private final Parser parser;
    
    private static final HashSet<String> CLAUSAL_TAGS;
    
    static {
        CLAUSAL_TAGS = new HashSet<>( 9 );
        CLAUSAL_TAGS.add( "S" );
        CLAUSAL_TAGS.add( "SINV" );
        CLAUSAL_TAGS.add( "SBAR" );
        CLAUSAL_TAGS.add( "RRC" );
        CLAUSAL_TAGS.add( "SBARQ" );
        CLAUSAL_TAGS.add( "SQ" );
        CLAUSAL_TAGS.add( "S-CLF" );
        CLAUSAL_TAGS.add( "FRAG" );
        CLAUSAL_TAGS.add( "X" );  // rarely used, non-clausal and marks unrecognised text
        CLAUSAL_TAGS.add( "TOP" ); // Apache OpenNLP appears to wrap everything in TOP
    }
    
    NameParser(PosTaggerType tt) {
        try (InputStream is = this.getClass().getResourceAsStream( PARSER_MODEL_PATH ) ) {
            this.parserModel = new ParserModel(is);
        }
        catch(FileNotFoundException e) {
            LOGGER.error( 
                    "Unable to locate PoS tagger model at {0}", 
                    PARSER_MODEL_PATH, 
                    e );
            throw new IllegalStateException("PoS Tagger model not found.", e);
        }
        catch(IOException e) {
            LOGGER.error( 
                    "Encountered problem reading PoS tagger model at {0}", 
                    PARSER_MODEL_PATH, 
                    e );
            throw new IllegalStateException( 
                    String.format( 
                            "Unable to read PoS tagger model at \"%s\"", 
                            PARSER_MODEL_PATH), 
                    e);
        }
        
        this.tagger = NameTaggerFactory.getInstance().create(tt);
        this.parserModel.updateTaggerModel(tagger.model());
        
        this.parser = ParserFactory.create(parserModel);
    }
    
    /**
     * Parses a name and returns a parse tree in Penn Treebank format, 
     * e.g.&nbsp;(S (NP (NNP Romeo)) (VP (VBZ loves) (NNP Juliet))).
     * @param tokens a list of identifier name tokens
     * @return a Penn Treebank parse tree as a string
     */    
    @Override
    public String parseTree(final List<String> tokens) {
        StringBuffer sb = new StringBuffer();
        String nameString = tokens.stream().collect(Collectors.joining(" "));
        
        Parse topParses[] = ParserTool.parseLine(nameString, this.parser, 1);
        Parse p = topParses[0];
        p.show(sb);
        return sb.toString();
    }

    /**
     * Parses a name and returns a phrasal summary of the parse tree as one or 
     * more phrases, e.g.&nbsp;(S (NP (NNP Romeo)) (VP (VBZ loves) (NNP Juliet))) 
     * is summarised as NP VP.
     * 
     * @param tokens a list of identifier name tokens 
     * 
     * @return A string containing phrasal tags.
     */
    @Override
    public String summarise(final List<String> tokens) {
        String nameString = tokens.stream().collect(Collectors.joining(" "));
        
        Parse topParses[] = ParserTool.parseLine(nameString, this.parser, 1);
        Parse p = topParses[0];
        
        Tree t = new Tree(p);
        return treeSummary(t);
    }
    
    /**
     * Recovers a summary of a given tree as one or more phrases, 
     * e.g. (S (NP (NNP Romeo)) (VP (VBZ loves) (NNP Juliet))) is summarised 
     * as NP VP.
     * 
     * @param parseTree A Stanford parse Tree
     * 
     * @return A string containing phrasal tags.
     */
    private String treeSummary( Tree parseTree ) {
        StringBuilder phraseTags = new StringBuilder();
        
        // now extract the top level phrases
        String value = parseTree.value();
        if ( isClausalTag( value ) ) {
            // recurse through children
            return parseTree.children().stream()
                    .map( c -> treeSummary(c) )
                    .collect( Collectors.joining( " " ) );
        }
        else {
            // we should have a phrasal tag
            // unlikely on first pass - but possible where there is a phrasal tag at top level
            phraseTags.append( value );
            System.out.println( value );
        }
        
        return phraseTags.toString();
    }
    
    private boolean isClausalTag( String tag ) {
        return CLAUSAL_TAGS.contains( tag );
    }
    
    private class Tree {
        private final List<Tree> children;
        private final String value;
        
        Tree(Parse p) {
            this.value = p.getType();
            this.children = new ArrayList<>();
            for(Parse child : p.getChildren()) {
                this.children.add( new Tree(child));
            }
        }
        
        List<Tree> children() {
            return this.children;
        }
        
        String value() {
            return this.value;
        }
    }
}
