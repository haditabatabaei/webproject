package hudson.scm;

import hudson.model.Run;
import java.io.IOException;
import java.net.URL;

import java.util.Collections;
import java.util.Iterator;


final class EmptyChangeLogSet extends ChangeLogSet<ChangeLogSet.Entry> {
     EmptyChangeLogSet(Run<?, ?> build) {
        super(build, new RepositoryBrowser<ChangeLogSet.Entry>() {
            @Override public URL getChangeSetLink(ChangeLogSet.Entry changeSet) throws IOException {
                return null;
            }
        });
    }

    @Override
    public boolean isEmptySet() {
        return true;
    }

    public Iterator<Entry> iterator() {
        return Collections.<Entry>emptySet().iterator();
    }
}