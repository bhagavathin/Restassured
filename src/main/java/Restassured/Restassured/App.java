package Restassured.Restassured;

import static com.jayway.restassured.RestAssured.get;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
//import static org.junit.Assert.assertTrue;
////import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;

import Restassured.Restassured.Constants;
import Restassured.Restassured.Xls_Reader;
import Restassured.Restassured.Keywords;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.*;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//import static com.jayway.restassured.RestAssured.expect; 
//import org.jbehave.core.annotations.Aliases;
//import org.jbehave.core.annotations.Given;
//import org.jbehave.core.annotations.Then;
//import org.jbehave.core.annotations.When;

/**
 * Hello world!
 *
 */

public class App 

{
	   Keywords keyword1=new Keywords();
	public static void main( String[] args ) 
    {
       App test=new App();
		
//       System.setProperty("https.proxyHost", "campus-proxy");
//		System.setProperty("https.proxyPort", "80");
		//Response res=given().proxy("10.150.5.182", 80).get("http://www.thomas-bayer.com/sqlrest/");
 
		//System.out.println(res.asString());
        	
        try {
			test.ReadExcel_Data2("Automation_Run_Report");
			
		} catch (AbstractMethodError | BiffException | WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    }
	
    public void testGetSingleUser(String URL) {
   	 	get(URL).then(). assertThat().
    	    body(
    	      "", equalTo("test@hascode.com"),   	        	      
    	      "firstName", equalTo("Tim"),
    	      "lastName", equalTo("Testerman"),
   	 		  "id", equalTo("1"));
   	 		  System.out.println("Json file excuted sucessfully");
    } 
    public void testGetSingleUserAsXml(String URL) {
    	get(URL).then().assertThat().  
    		body(
    				"user.email", equalTo("test@hascode.com"),
    				"user.firstName", equalTo("Tim"),
    				"user.lastName", equalTo("Testerman"),
    				"user.id", equalTo("1")); 
    	 		System.out.println("XML file excuted sucessfully");  	 		
    }
    
    public void testGetPersons(String URL) {
    	}
    
    
    public void Verify_Statuscode(String apiurl){    	
    	Response res = get(apiurl);
    	System.out.println(res.statusCode());  	     	  
    }  
    public boolean TextValidation1(String url,String str1,String str2) 
    {
    		System.out.println("TextValidation");
    		// try
    		// {
    		  get(url).then().assertThat().body(str1, equalTo(str2));
    		  return true;
    		 }
//    		 catch (Exception e)
//    		 { return false;}
//    }    	  		     
              

    public void spiltstring( Xls_Reader current_TestCase_xls,int rowNum,String url,String S1, String S2) 
    {
       	String joined = null;
    	String[] GetOutputKey = S1.split("\\|");
    	String[] Actual = S2.split("\\|");
    	String[] descrition = new String[GetOutputKey.length];
    	String Status=null;
    	System.out.println(GetOutputKey.length);
    	System.out.println(Actual.length);
    	System.out.println(GetOutputKey[0]);
    	boolean TestFalg=true; 	
    	boolean[] result = new boolean[GetOutputKey.length];
		for(int i=0;i<GetOutputKey.length;i++)
		{	
			System.out.println(GetOutputKey[i]);
			System.out.println(Actual[i]);
		 	result[i]= TextValidation(url, GetOutputKey[i], Actual[i]);
			if (result[i] == true)
			 descrition[i]= "Validation match " + GetOutputKey[i]+":" +Actual[i];				
			else{
				TestFalg=false;
				descrition[i]= "Validation not match " + GetOutputKey[i]+":" +Actual[i];
				}
				System.out.println(descrition[i]);
		  } 			
			joined = String.join("|", descrition);
			if (TestFalg=true)
			 Status ="PASS"; 
			else
			 {Status="FAIL";}
			
			boolean SetValue3=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Description", rowNum, joined);
			boolean SetValue4=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, Status);
		}
	
    public boolean TextValidation(String url,String Fieldname,String ExpectValue)
    {
    	Response res = get(url);
    	//System.out.println(res.statusCode());
    	String responseString = get(url).asString();
    	System.out.println("Response String:" +responseString);
    	System.out.println("Fieldname String:" +Fieldname);
    	System.out.println("ExpectValue String:" +ExpectValue);
        get(url).then().assertThat().body(Fieldname,equalTo(ExpectValue));
    	return true;
    }

    public int Verify_Statuscode(String apiurl,int StatusCode){          
       System.out.println("Verify_Statuscode excuted sucessfully"+apiurl+StatusCode);
       return StatusCode;
    }
    public int Get_ResponseMessage(String apiurl)
    {    
    	int StatusCode;
    	Response res = get(apiurl);
    	StatusCode=res.statusCode();     
    	if (StatusCode==200)
    	{
    		String body = res.getBody().asString();
        	System.out.println("Device is sucessfully added and reponse is "+body);
        	return StatusCode;
    	}
    	else
    	{
    		System.out.println("Device is not added and  status code is "+res.getStatusCode());
    		return StatusCode;
    	}
   }
    public String Get_ResponseMessage2(String apiurl)
    {    
    	String ResMessage;
    	Response res = get(apiurl);
    	ResMessage=res.getBody().toString();
    	System.out.println("Response message for API"+apiurl+" is "+ResMessage);
    	return ResMessage;	
   }
  
    public Response Get_Response(String apiurl){    
    	String ResMessage;
    	int StatusCode;
    	Response res = given().
        when().
        get(apiurl);
    	return res;
   }
    
    public void Get_XMLResponse(String apiurl){    
    	String ResMessage;
    	int StatusCode;
    	Response res = given().
    	        when().
    	        get(apiurl);
   }
    
    public void StrFns(String XString)
    {
    	String Str,Str2,ResStr1,ResStr2,ResStr3,ResStr4;
        if (XString.contains(Constants.KEYWORD_REF))
        {
        	String[] A1tokens=XString.split(Constants.KEYWORD_REF);
        	ResStr1=A1tokens[0];
        	if (ResStr1.charAt(ResStr1.length()-1)=='{')
        	{
        	ResStr1 = ResStr1.replace(ResStr1.substring(ResStr1.length()-1), "");
        	}
        	ResStr2=A1tokens[1];
        	if (ResStr2.charAt(ResStr2.length()-1)=='}')
        	{
        		ResStr2 = ResStr2.replace(ResStr2.substring(ResStr2.length()-1), "");
        		if (ResStr2.contains("|"))
        		{
        			 String[] ArValues = ResStr2.split(Constants.DATA_SPLIT);
        			ResStr3=ArValues[0];
        			ResStr4=ArValues[1];
                	System.out.println("String3 : "+ResStr3); 	
                	System.out.println("String4 : "+ResStr4); 
        		}
        	}
        	
        	System.out.println("String1 : "+ResStr1); 	
        	System.out.println("String2 : "+ResStr2); 	
        }
    }

    public Response Post_GetResponseMessage(String apiurl,String PKeys, String PValues){    
    	int StatusCode;
    	StatusCode=1;
    	
    	Map<String, Object>  jsonAsMap = new HashMap();
        String[] ArKeys = PKeys.split(Constants.DATA_SPLIT);
        String[] ArValues = PValues.split(Constants.DATA_SPLIT);
        for(int i =0; i < (ArKeys.length & ArValues.length) ; i++)
        {
        	jsonAsMap.put(ArKeys[i], ArValues[i]);
        }
    	Response res=given()
    	.contentType("application/json;charset=UTF-8")
		.body(jsonAsMap)
		.when()
		.post(apiurl);
    	StatusCode=res.getStatusCode();
    	if (StatusCode==200)
    	{
    		String body = res.getBody().asString();
        	System.out.println("Device is sucessfully added and reponse is "+body);
        	return res;
    	}
    	else
    	{
    		System.out.println("Device is not added and  status code is "+res.getStatusCode());
    		return res;
    	}

   }
    
    public void Parse_ResponseMessage(Response ResMsg)
    {
        JsonPath jsonPath = new JsonPath(ResMsg.toString());
    	String Device_Name =jsonPath.toString();
    	System.out.println("Device Name : "+Device_Name);
    	String Device_Name2=jsonPath.setRoot("data").getString("_id").toString();
    	System.out.println("Device Name : "+Device_Name2);
    }
  
    public void ReadExcel_Data2(String File_name)throws AbstractMethodError, BiffException, IOException,  WriteException
    {
    	 
		  	Xls_Reader current_TestCase_xls=null;		  
            String sSelected,APIType,APIURL,MethodType,ParamKey,ParamValues,Expected,Result,Actual,TCID,OpKey,WebServices,RunMode1,SwitchingMode;
            String OutValues;
            
            current_TestCase_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//res//Automation_Run_Report.xlsx");
     	 	int TEST_CASES_rows= current_TestCase_xls.getRowCount(Constants.TEST_CASES_SHEET);
     	 	System.out.println("TEST_CASES_rows" +TEST_CASES_rows);
			for(int rowNum1=2;rowNum1<=TEST_CASES_rows;rowNum1++)
				{	
					String RunMode=current_TestCase_xls.getCellData(Constants.TEST_CASES_SHEET, "RunMode", rowNum1);
					System.out.println("Excecution" +RunMode+rowNum1);
					if (RunMode.equalsIgnoreCase("YES"))					
					{
						System.out.println("Excecution if" +RunMode);
						Constants.TEST_STEPS_SHEET=current_TestCase_xls.getCellData(Constants.TEST_CASES_SHEET, "SuiteName", rowNum1);
						System.out.println("TestSheet name" +Constants.TEST_STEPS_SHEET);
			     	 	int TEST_STEPS_rows= current_TestCase_xls.getRowCount(Constants.TEST_STEPS_SHEET);			
						for(int rowNum=2;rowNum<=TEST_STEPS_rows;rowNum++){
							APIType="";				
							TCID=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "TCID", rowNum);
							APIURL=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "URL", rowNum);
							//Get reference value
							OpKey=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "GetOutputKey", rowNum);
							APIURL= this.GetReferenceValue(APIURL);
							APIType=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "RequestType", rowNum).trim();
							MethodType=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "MethodType", rowNum);
						//	System.out.println("MethodType"+MethodType);
							ParamKey=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "Param_Keys", rowNum);
							ParamValues=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "Param_Values", rowNum);
							Expected=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "Expected", rowNum);		
							Actual=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "Actual", rowNum);
							//Get reference value							
							WebServices=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "WebServices", rowNum);
							
							RunMode1=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "RunMode", rowNum);
							//System.out.println("RunMode1:"+RunMode1);
							ParamValues= this.GetReferenceValue(ParamValues);
							System.out.println("Column Wise"+APIURL+APIType+MethodType+ParamKey+ParamValues+Expected+Actual+WebServices+RunMode1);
							if (RunMode1.equalsIgnoreCase("YES"))
							{	
								System.out.println("Inside run mode");
							switch(WebServices.toUpperCase())
							{
							case "UI":
								//System.out.println("UI Switch case");
								//WebDriver driver;
								keyword1.openBrowser("test","IE");
								keyword1.navigate("test",APIURL);
								String s1=keyword1.VerifyWebTable("test", "//tr/td[contains(text(),'"+Expected+"')]");
								String s2=keyword1.VerifyImage("test", "//div[@class='row placeholders']//h4[(text()='"+Expected+"')]");
								
								if (s1.length() > 0  && s2.length() > 0)
								{	
								 boolean des=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Description", rowNum, "Device "+Expected+" Validated in UI sucessfully - matched");
								 boolean status=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "PASS");
								}
								else
								{
									boolean des=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Description", rowNum, "Device "+Expected+" Validated in UI - un matched");
									boolean status=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "FAIL");	
								}
								 break;
							case "DATABASE":
								boolean database=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Description", rowNum, "Need to add Framework for DataBase Services");
								break;
							case "API":	
								System.out.println("Inside API"+MethodType);
							switch(MethodType) 
							{
								    
									case "POST":
										System.out.println("insidepost"+MethodType);
								    	Response Pres= this.Post_GetResponseMessage(APIURL,ParamKey,ParamValues);
								    	int PStatusCode=Pres.getStatusCode();
								    	if (PStatusCode==200)
								    	{
								        	System.out.println("The reponse for "+APIURL+" is "+Pres.asString());								        	
								        	this.StoreRespose(current_TestCase_xls,TCID,APIType,Pres.asString(),rowNum);
								        	boolean SetValue1=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "Pass");			        	        	   	
								        	OutValues=GsonStreamExample2(TCID,APIType,OpKey);
								        	SwitchingMode=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "SwitchingMode", rowNum);
								        	String[] switching = SwitchingMode.split("\\|");
								           // String[] testcase = new String[switching.length];
								        	String Status=null;								        	
								        //	boolean[] result = new boolean[switching.length];
								        	String switchValues=GsonStreamExample2(TCID,APIType,switching[0]);	
								    		for(int i=1;i<switching.length ;i++)
								    		{								    			
								    			String[] tc=switching[i].split(":");
								    			
								    			if (switchValues.equalsIgnoreCase(tc[0]))
								    			{		
									    			int rownum2=current_TestCase_xls.getCellRowNum(Constants.TEST_STEPS_SHEET, "TCID",tc[1]);
									    			boolean SetValue3=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "RunMode", rownum2, "YES");
									    			String URL=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "URL", rownum2);
									    			boolean URLupdate=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "URL", rownum2, URL+"/"+OutValues);
								    			}	
								    		}
								        	boolean SetValue3=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Output_Values", rowNum, OutValues);
								        	boolean SetValue4=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Description", rowNum,ParamValues+" : Sucessfully posted");
								    	}
								    	else
								    	{
								    		System.out.println("The status code for "+APIURL+" is "+PStatusCode);
								    		boolean SetValue1=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "Fail");
								        }					
								        break;
								    case "GET":
//								    	System.out.println("insideget"+MethodType);
////								    	System.setProperty("https.proxyHost", "10.150.5.183");
////								    	System.setProperty("https.proxyPort", "80");
//								    	RestAssured.proxy("10.150.5.183", 80);
//								    	
//								    	
//								    //	RestAssured.proxy = host("caproxympus-").withPort(80);
//								    	Response Gres= given().proxy("10.150.5.182", 80).get(APIURL);	
								    	
								    //	given().proxy(host("10.150.5.183").withAuth("hcltech\bhagavathi.n", "jkgayathri@16"));
								    	//Response Gres = get(APIURL);
								    	 Response Gres = null;
								    	
								    	switch(APIType) 
							        	{
							        	case "XML" :
							        		 Gres= given().proxy("10.150.5.182", 80).get(APIURL);	
							        		break;
							        		
							        	case "JSON":
							        		 Gres=get(APIURL);
							        		 break;
							        	}
								    	
								    	//Response Gres=get(APIURL);
								    	this.StoreRespose(current_TestCase_xls,TCID,APIType,Gres.asString(),rowNum);
								    	int GStatusCode=Gres.getStatusCode();
								    	System.out.println("The status code for "+APIURL+" is "+GStatusCode);
								    	if (GStatusCode==200)
								    	{
								        	System.out.println("The reponse for "+APIURL+" is "+Gres.asString());
								        	this.StoreRespose(current_TestCase_xls,TCID,APIType,Gres.asString(),rowNum);
							        	    System.out.println(TCID+" : "+APIType+"Key :"+OpKey);								        	
								        	System.out.println("APITYPE: "+APIType);						        	
								        	switch(APIType)
								        	{
								        	case "XML" :
								        		System.out.println("outputkey: "+OpKey);
								        		if (Expected.length() > 0 )
									    			spiltstring(current_TestCase_xls,rowNum,APIURL,OpKey,Expected);
								        		else{
								        		OutValues=XMLGsonStreamExample2(TCID,APIType,OpKey);
								        		System.out.println("OutValues"+OutValues);
								        		SwitchingMode=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "SwitchingMode", rowNum);
									        	String[] switching = SwitchingMode.split("\\|");
									        	System.out.println("switching:"+switching.length+SwitchingMode);
									           // String[] testcase = new String[switching.length];
									        	String Status=null;								        	
									        //	boolean[] result = new boolean[switching.length];
									        	System.out.println("switching[0]"+switching[0]);
									        	String switchValues=XMLGsonStreamExample2(TCID,APIType,switching[0]);	
									    		for(int i=1;i<switching.length ;i++)
									    		{								    			
									    			String[] tc=switching[i].split(":");
									    			System.out.println("switchValues"+switchValues);
									    			if (switchValues.equalsIgnoreCase(tc[0]))
									    			{		
										    			int rownum2=current_TestCase_xls.getCellRowNum(Constants.TEST_STEPS_SHEET, "TCID",tc[1]);
										    			boolean SetValue3=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "RunMode", rownum2, "YES");
										    			String URL=current_TestCase_xls.getCellData(Constants.TEST_STEPS_SHEET, "URL", rownum2);
										    			boolean URLupdate=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "URL", rownum2, URL+"/"+OutValues);
										    			boolean SetValue1=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "PASS");
										    			System.out.print("URLupdate :"+URLupdate);
									    			}	
									    		}
									    		boolean SetValue3=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Output_Values", rowNum, OutValues);
								        		}
									    	   	break;
										    case "JSON" :
										    	System.out.println("SwitchAPITYPE: "+APIType);
										    	spiltstring(current_TestCase_xls,rowNum,APIURL,OpKey,Expected);
										    	break;
										    }	        	
								    	} 
								    	else
								    	{
								    		System.out.println("The status code for "+APIURL+" is "+Gres.getStatusCode());
								    		boolean SetValue1=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Status", rowNum, "Fail");
								        	boolean SetValue2=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Response", rowNum, Gres.asString());
								    	}
								    	
								        break;
								    	case "DELETE":
								    		break;
								    default:
							}
							break;
							default:
						   }
						}
					  }
						
					}
				}
           } 
    
    
    
  
	public String GetReferenceValue(String XString)
    {
    	String Str,Str2,ResStr1,ResStr2,ResStr3,ResStr4,OpValue;
    	ResStr4=null;
    	String ReTurnString=XString;
    	Xls_Reader current_TestCase_xls1=null;
        current_TestCase_xls1=new Xls_Reader(System.getProperty("user.dir")+"//src//res//Automation_Run_Report.xlsx");
    	if (XString.contains(Constants.KEYWORD_REF))
        {
        	String[] A1tokens=XString.split(Constants.KEYWORD_REF);
        	ResStr1=A1tokens[0];
        	if (ResStr1.charAt(ResStr1.length()-1)=='{')
        	{
        	ResStr1 = ResStr1.replace(ResStr1.substring(ResStr1.length()-1), "");
        	}
        	ResStr2=A1tokens[1];
        	if (ResStr2.charAt(ResStr2.length()-1)=='}')
        	{
        		ResStr2 = ResStr2.replace(ResStr2.substring(ResStr2.length()-1), "");
        		if (ResStr2.contains("|"))
        		{
        			 String[] ArValues = ResStr2.split(Constants.DATA_SPLIT);
        			ResStr3=ArValues[0];
        			ResStr4=ArValues[1];
                	System.out.println("Ref Testcase ID : "+ResStr3); 	
                	int ValueRow= current_TestCase_xls1.getCellRowNum(Constants.TEST_STEPS_SHEET, "TCID", ResStr3);
                	OpValue=current_TestCase_xls1.getCellData(Constants.TEST_STEPS_SHEET, "Output_Values", ValueRow);
                	System.out.println(ResStr4+" Value  of "+ResStr3+" is "+OpValue);
                	ReTurnString=ResStr1+OpValue;
        		}
        	}
        }
        System.out.println("Return String:"+ReTurnString);
    	return ReTurnString;
    }
    public void StoreRespose(Xls_Reader current_TestCase_xls,String Filename, String FileType,String Value,int rowNum)throws FileNotFoundException
    {
    	File file = new File(System.getProperty("user.dir")+"//src//res//Response//"+Filename+"."+FileType);
		try (FileOutputStream fop = new FileOutputStream(file)) {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = Value.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
//			URL myUrl=file.toURL();
			URL myUrl = file.toURI().toURL();
			System.out.println("Filepath"+ myUrl.toString());
				//boolean SetValue2=current_TestCase_xls.setCellData1(Constants.TEST_STEPS_SHEET, "Response",rowNum, Filename,myUrl.toString());
				boolean SetValue2=current_TestCase_xls.setCellData(Constants.TEST_STEPS_SHEET, "Response", rowNum, myUrl.toString());
				//boolean SetValue2=current_TestCase_xls.setCellData1(Constants.TEST_STEPS_SHEET, "Response",rowNum, Filename,myUrl.toURI());
			System.out.println("Done");
		}
     catch (IOException e) {e.printStackTrace();}
    }
   
    
    public void GSONReadingFromFileExample()
    { 
    	Gson gson = new Gson(); 
    	try 
    	{ 
    		System.out.println("Reading JSON from a file"); 
	    	System.out.println("----------------------------"); 
	    	BufferedReader br = new BufferedReader( new FileReader("E:\\file.json")); 
	    	//convert the json string back to object 
//	    	Country countryObj = gson.fromJson(br, Country.class); 
//	    	System.out.println("Name Of Country: "+countryObj.getName()); 
//	    	System.out.println("Population: "+countryObj.getPopulation()); 
//	    	System.out.println("States are :"); 
//	    	List<String> listOfStates = countryObj.getListOfStates(); 
//	    	for (int i = 0; i < listOfStates.size(); i++) 
//	    	{ 
//	    		System.out.println(listOfStates.get(i)); 
//	    	
//	    	}
//	    	
//	    	Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
//        	map.forEach((x,y)-> System.out.println("key : " + x + " , value : " + y));
    	}
    	catch (IOException e) 
    	{ 
    		e.printStackTrace(); 
    	} 
    }
    public String GsonStreamExample3(String FileName,String FileType,String Key)
    {
    	String ReString;
    	ReString=null;
    	ReString="Not Found";
	    File file = new File(System.getProperty("user.dir")+"//src//res//Response//"+FileName+"."+FileType);
	    try 
	        {
	        	JsonReader reader = new JsonReader(new FileReader(file));
	        	reader.beginObject();
	        	while (reader.hasNext()) 
	        	{
	        		String messgae = reader.nextName();
	        		if (messgae.equals(Key)) 
	        		{        			
	        			ReString=reader.nextString();	        			
	        		} 
	        		else 
	        		{
	        			reader.skipValue(); //avoid some unhandle events
	        		}		
	        	}	
	        	reader.endObject();
	        	reader.close();
	        } 
        	catch (FileNotFoundException e)
	    		{e.printStackTrace();}
        	catch (IOException e) 
        		{e.printStackTrace();}
        	System.out.println("Json Value  "+Key+" is "+ReString); 	
        	return ReString;
    	}
      
    public String GsonStreamExample2(String FileName,String FileType,String Key)
    {   	
    	String ReString;
    	ReString=null;
    	ReString="Not Found";
	    File file = new File(System.getProperty("user.dir")+"//src//res//Response//"+FileName+"."+FileType);
	    try 
	        {
	        	JsonReader reader = new JsonReader(new FileReader(file));
	        	reader.beginObject();
	        	while (reader.hasNext()) 
	        	{
	        		String messgae = reader.nextName();
	        		if (messgae.equals("message")) 
	        		{
	        			ReString=reader.nextString();
	        		} 
	        		else if (messgae.equals("data")) 
	        		{
	        			reader.beginObject();
				   		while (reader.hasNext()) 
				   		{
				   			String data = reader.nextName();
				   			if (data.equals(Key)) 
			        		{			        
			        			ReString=reader.nextString();
			           		} 
				   			else 
			        		{
			        			reader.skipValue(); //avoid some unhandle events
			        		}				   		}
				   		reader.endObject();
	        		} 
	        		else if (messgae.equals(Key)) 
	        		{        			
	        			ReString=reader.nextString();	        			
	        		} 
	        		else 
	        		{
	        			reader.skipValue(); //avoid some unhandle events
	        		}
	           }	
	        	reader.endObject();
	        	reader.close();	        	
	        } 
	        catch (FileNotFoundException e) 
	        {
	        	e.printStackTrace();
	        } 
	        catch (IOException e) 
	        {
	        	e.printStackTrace();
	        }	        
	       System.out.println("Json Value  "+Key+" is "+ReString); 	
     	return ReString;
      }
    
    
    
    public String XMLGsonStreamExample2(String FileName,String FileType,String Key)
    {
    	String ReString;
    	ReString=null;
    	ReString="Not Found"; 
    	      try {				
		    	  	System.out.println("XML Value for : "+FileName+" : " +FileType);	
				  	File fXmlFile = new File(System.getProperty("user.dir")+"//src//res//Response//"+FileName+"."+FileType);
				  	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				  	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();				 
				  	Document doc = dBuilder.parse(fXmlFile);
				  	doc.getDocumentElement().normalize();	  				  		
				  	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());		
				  	String roolElement=doc.getDocumentElement().getNodeName().toString();	
				  	System.out.println("Key1 :" + Key);	
				  	String s1= Key;
				  	System.out.println("Key1 Len :"+Key.length());
				    NodeList nList = doc.getElementsByTagName(roolElement);				  	
				  	System.out.println("length of the node"+nList.getLength());
				  	
				  	for (int temp = 0; temp < nList.getLength(); temp++)
				  	{				
				  		Node nNode = nList.item(temp);	
				  		
				  		if (nNode.getNodeType() == Node.ELEMENT_NODE) {				
				  			Element eElement = (Element) nNode;	
				  			ReString=eElement.getElementsByTagName(Key.trim()).item(0).getTextContent();				  		
				  			System.out.println("XML Value for  "+Key+" : " +ReString);			
				  			}
				  	}
				  } 
		      catch (Exception e) {e.printStackTrace();}
    	return ReString;
     }
}

    
	
