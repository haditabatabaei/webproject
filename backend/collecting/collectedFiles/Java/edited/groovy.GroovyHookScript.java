package jenkins.util.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import jenkins.model.Jenkins;


public class GroovyHookScript {
    private final String hook;
    private final Binding bindings = new Binding();
    private final ServletContext servletContext;
    private final File home;
    private final ClassLoader loader;

    @Deprecated
    public GroovyHookScript(String hook) {
        this(hook, Jenkins.getActiveInstance());
    }

    private GroovyHookScript(String hook, Jenkins j) {
        this(hook, j.servletContext, j.getRootDir(), j.getPluginManager().uberClassLoader);
    }

    public GroovyHookScript(String hook, @Nonnull ServletContext servletContext, @Nonnull File home, @Nonnull ClassLoader loader) {
        this.hook = hook;
        this.servletContext = servletContext;
        this.home = home;
        this.loader = loader;
    }

    public GroovyHookScript bind(String name, Object o) {
        bindings.setProperty(name,o);
        return this;
    }

    public Binding getBindings() {
        return bindings;
    }

    public void run() {
        final String hookGroovy = hook+".groovy";
        final String hookGroovyD = hook+".groovy.d";

        try {
            URL bundled = servletContext.getResource("/WEB-INF/"+ hookGroovy);
            execute(bundled);
        } catch (IOException e) {
            LOGGER.log(WARNING, "Failed to execute /WEB-INF/"+hookGroovy,e);
        }

        Set<String> resources = servletContext.getResourcePaths("/WEB-INF/"+ hookGroovyD +"/");
        if (resources!=null) {
                        for (String res : new TreeSet<String>(resources)) {
                try {
                    URL bundled = servletContext.getResource(res);
                    execute(bundled);
                } catch (IOException e) {
                    LOGGER.log(WARNING, "Failed to execute " + res, e);
                }
            }
        }

        File script = new File(home, hookGroovy);
        execute(script);

        File scriptD = new File(home, hookGroovyD);
        if (scriptD.isDirectory()) {
            File[] scripts = scriptD.listFiles(new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().endsWith(".groovy");
                }
            });
            if (scripts!=null) {
                                Arrays.sort(scripts);
                for (File f : scripts) {
                    execute(f);
                }
            }
        }
    }

    protected void execute(URL bundled) throws IOException {
        if (bundled!=null) {
            LOGGER.info("Executing bundled script: "+bundled);
            execute(new GroovyCodeSource(bundled));
        }
    }

    protected void execute(File f) {
        if (f.exists()) {
            LOGGER.info("Executing "+f);
            try {
                execute(new GroovyCodeSource(f));
            } catch (IOException e) {
                LOGGER.log(WARNING, "Failed to execute " + f, e);
            }
        }
    }

    protected void execute(GroovyCodeSource s) {
        try {
            createShell().evaluate(s);
        } catch (RuntimeException x) {
            LOGGER.log(WARNING, "Failed to run script " + s.getName(), x);
        }
    }

    
    protected GroovyShell createShell() {
        return new GroovyShell(loader, bindings);
    }

    private static final Logger LOGGER = Logger.getLogger(GroovyHookScript.class.getName());
}