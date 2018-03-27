

package org.elasticsearch.indices;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.index.Index;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Arrays;

public class TypeMissingException extends ElasticsearchException {

    public TypeMissingException(Index index, String... types) {
        super("type" + Arrays.toString(types) + " missing");
        setIndex(index);
    }

    public TypeMissingException(Index index, Throwable cause, String... types) {
        super("type" + Arrays.toString(types) + " missing", cause);
        setIndex(index);
    }

    public TypeMissingException(String index, String... types) {
        super("type[" + Arrays.toString(types) + "] missing");
        setIndex(index);
    }

    public TypeMissingException(StreamInput in) throws IOException{
        super(in);
    }

    @Override
    public RestStatus status() {
        return RestStatus.NOT_FOUND;
    }
}
