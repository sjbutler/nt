 package uk.org.facetus.nt;

/**
 * Groups a token and its PoS tag.
 * 
 */
public class PosTaggedToken {
    private final String token;
    private final String tag;
    private final String normalisedToken;
    
    /**
     * Creates a tagged token.
     * @param token a token found in a name
     * @param tag  a part of speech tag
     */
    PosTaggedToken( final String token, final String tag ) {
        this.token = token;
        this.tag = tag;
        this.normalisedToken = token.toLowerCase();
    }
    
    /**
     * Retrieves the token. 
     * @return the token
     */
    public final String token() {
        return this.token;
    }
    
    /**
     * Retrieves the PoS tag assigned to the token.
     * @return the PoS tag assigned to the token
     */
    public final String tag() {
        return this.tag;
    }
    
    /**
     * Recovers the token normalised to lower case.
     * @return the token in lower case
     */
    public final String normalisedToken() {
        return this.normalisedToken;
    }
}
