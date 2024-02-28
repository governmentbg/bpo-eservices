package eu.ohim.sp.common;

import com.sun.codemodel.*;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 20/10/2013
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class PrivateMemberPlugin extends Plugin {
    @Override
    public String getOptionName() {
        return "Xprivate";
    }

    @Override
    public String getUsage() {
        return "Xprivate    : replaces non private members with private";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) throws SAXException {
        for (ClassOutline co : outline.getClasses()) {

            JDefinedClass jdc = co.implClass;
            // avoid concurrent modification by copying the fields in a new list
            List<JFieldVar> fields = new ArrayList<JFieldVar>(jdc.fields().values());
            for (JFieldVar field : fields) {
                // never do something with serialVersionUID if it exists.
                if (!field.name().equalsIgnoreCase("serialVersionuid")) {
                    // only try to change members that are not private
                    if (field.mods().getValue() != JMod.PRIVATE) {
                        field.mods().setPrivate();
                    }
                }
            }

        }
        return true;
    }
}
