package gurankio.util;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.concurrent.ConcurrentLinkedDeque;

public class JShellEvalWrapper {

    private static final ConcurrentLinkedDeque<String> values = new ConcurrentLinkedDeque<>();
    private static final JShell js = JShell.create();

    static {
        js.eval("import static java.lang.Math.*;");
        js.onSnippetEvent(JShellEvalWrapper::addValue);
    }

    private static void addValue(SnippetEvent event) {
        values.push(event.value() == null ? "" : event.value());
    }

    public static String eval(String data) {
        js.eval(data);
        // Busy waiting because i can't get threads to work
        while (values.size() < 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = values.pop();
        if (result.equals("")) return data;
        return result;
    }

}
