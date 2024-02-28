package eu.ohim.sp.common.path;

import javax.enterprise.inject.Alternative;

/**
 * Plain, dummy implementation of {@link PathResolutionStrategy} just assumes that path given by argument is full absolute path
 *
 * @author Maciej Walkowiak
 */
@Alternative
public class AbsolutePathFileResolutionStrategy implements PathResolutionStrategy {

    @Override
    public String resolvePath(String path) {
    	if(path == null){
    		throw new IllegalArgumentException("path cannot be null");
    	}

        return path;
    }
}
