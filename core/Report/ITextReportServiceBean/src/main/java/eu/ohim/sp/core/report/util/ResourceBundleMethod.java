package eu.ohim.sp.core.report.util;

import freemarker.template.*;

import java.util.List;

public class ResourceBundleMethod implements TemplateMethodModelEx {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		try {
			if (arguments.size() != 3) {
				throw new IllegalArgumentException("3 arguments required");
			}
			
			if (! (arguments.get(0) instanceof  TemplateHashModelEx2)) {
				throw new IllegalArgumentException("first argument should be TemplateHashModelEx2");
			}
			
			if (! (arguments.get(1) instanceof TemplateScalarModel)) {
				throw new IllegalArgumentException("second argument should be TemplateScalarModel");
			}

            if (! (arguments.get(2) instanceof  TemplateSequenceModel)) {
                throw new IllegalArgumentException("third argument should be  TemplateSequenceModel");
            }

			TemplateHashModelEx2 messages = (TemplateHashModelEx2) arguments.get(0);
			TemplateScalarModel key = (TemplateScalarModel) arguments.get(1);
			TemplateSequenceModel args = (TemplateSequenceModel) arguments.get(2);
			
			if(key.getAsString() == null || "".equals(key.getAsString())) {
				throw new IllegalArgumentException("missing key");
			}
			
			Object result =  messages.get(key.getAsString());
			
			if (result == null) {
				result = "["+key+"]";
			} else if (args.size() > 0) {
			    String msg = result.toString();
				for (int i=0; i<args.size(); i++) {
					Object arg = args.get(i);
					if (arg != null) {
                        msg=msg.replaceAll("\\{"+i+"\\}", arg.toString());
					}
				}
				result = new SimpleScalar(msg);
			}
			
			return result;
		} catch (Exception e) {
			throw new TemplateModelException(e);
		}
	}

}
