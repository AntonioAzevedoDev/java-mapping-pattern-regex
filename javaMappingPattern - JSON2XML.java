public void transform(TransformationInput in, TransformationOutput out) throws StreamTransformationException {

String stacktrace;
String protocolo;
String mensagem;
String status;
        try{
    			String source = "";
			String line = "";
			String target = "";

			InputStream inStr = in.getInputPayload().getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inStr));
			while ((line = br.readLine()) != null) 
				source += line.toString(); 
		    
		    try{
            
   		    	Pattern pattern = Pattern.compile("stacktrace\":\"(.+)\",\"mensagem", Pattern.CASE_INSENSITIVE);
            	Matcher matcher = pattern.matcher(source.toString());
            	matcher.find();
             	stacktrace = matcher.group(1);
		    }
		    catch(Exception e){
		        stacktrace = "";
		    }
		    try{
            Pattern pattern2 = Pattern.compile("mensagem\":\"(.+)\",\"status", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(source);
            matcher2.find();
             mensagem = matcher2.group(1);
		    }
		    catch(Exception e){
		        mensagem = "";
		    }
		    try{
            Pattern pattern4 = Pattern.compile("protocolo\":(.+),\"status", Pattern.CASE_INSENSITIVE);
            Matcher matcher4 = pattern4.matcher(source);
            matcher4.find();
             protocolo = matcher4.group(1);
		    }
		    catch(Exception e){
						protocolo = "";
		    }
		    try{
        	Pattern pattern3 = Pattern.compile("status\":\"(.+)\"}", Pattern.CASE_INSENSITIVE);
            Matcher matcher3 = pattern3.matcher(source);
            matcher3.find();
             status = matcher3.group(1);
		    }
catch(Exception e){
		        status = "";
		    }
			if(protocolo == ""){
                target = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:MT_EnvioCTE_RES xmlns:ns0=\"http://lincros.com/EnvioCTE\"><stacktrace>" +
			    stacktrace + "</stacktrace><mensagem>" +
			    mensagem + "</mensagem><status>" +
			    status + "</status></ns0:MT_EnvioCTE_RES>";
			}
			else{
			    target = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:MT_EnvioCTE_RES xmlns:ns0=\"http://lincros.com/EnvioCTE\"><protocolo>" +
			    protocolo + "</protocolo><status>" +
			    status + "</status></ns0:MT_EnvioCTE_RES>";
			}
			
			out.getOutputPayload().getOutputStream().write(target.getBytes());
            br.close();
			}
    
			catch(Exception e){
			   throw new StreamTransformationException("Erro " + e.getMessage());
			}
}