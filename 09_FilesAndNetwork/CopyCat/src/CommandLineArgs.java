import com.beust.jcommander.Parameter;

import java.util.List;

class CommandLineArgs {
    @Parameter(names = "-r", description = "Recursive mode")
    private boolean recursive = false;   // значение по умолчанию
    @Parameter(names = "-p", description = "Preserve properties")
    private boolean preserve = false;
    @Parameter(names = "-o", description = "Overwrite files")
    private boolean overwrite = false;
    @Parameter(names = "-paths", arity = 2, description = "Paths")
    private List<String> paths;

    public boolean isRecursive() {
        return recursive;
    }

    public boolean isPreserve() {
        return preserve;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public List<String> getPaths() {
        return paths;
    }
}
