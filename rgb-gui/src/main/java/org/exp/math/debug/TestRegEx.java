package org.exp.math.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegEx {

    private String reStr;
    private Pattern testRe;
    private static final Pattern fracRe = Pattern.compile("^([0123456789\\.]+)?,([0123456789\\.]+)?,([0123456789\\.]+)?$");

    public TestRegEx(String reStr) {
        this.reStr = reStr;
        testRe = Pattern.compile(reStr);
    }

    public void setRegEx(String reStr) {
        testRe = Pattern.compile(reStr);
        this.reStr = reStr;
    }

    public String getRegEx() {
        return reStr;
    }

    public List<String> getGroups(String strIn) {
        List<String> groups = new ArrayList<String>();
        Matcher m = testRe.matcher(strIn);
        if (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                groups.add(m.group(i + 1));
            }
        }
        return groups;
    }
}
