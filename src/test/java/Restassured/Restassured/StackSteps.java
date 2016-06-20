package Restassured.Restassured;

import java.util.Stack;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.jayway.restassured.response.Response;
import Restassured.Restassured.App;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


import junit.framework.Assert;;

public class StackSteps{
	App Testap=new App();
	ReportUtil Rep=new ReportUtil();
	
	@When("the execute the JSON")

	public void whenTheExecuteTheJSON() throws AbstractMethodError, BiffException, WriteException, IOException{
		 //TODO 
		//Testap.ReadExcel_Data2("Automation_Run_Report");
		System.out.println("When");
	}
	@Then("Validate with expected result and store in excel")

	public void thenValidateWithExpectedResultAndStoreInExcel(){
		 //TODO 
		System.out.println("Then");
	}
	@Given("the Input sheet $appurl")
	public void givenTheInputSheetDWCRestassuredsrcresAutomation_Run_Reportxlsx(String sappurl) throws AbstractMethodError, BiffException, WriteException, IOException{
		 //TODO 
		Testap.ReadExcel_Data2("Automation_Run_Report");
		System.out.println("Given"+sappurl);
	}
	@AfterStories
	public void afterStories() throws Exception {
	    // ...
		Rep.GeneratehtmlReport();
	}

	
}