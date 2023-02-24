package driverFactory;
import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	String inputpath ="D:\\MrngBatch_OJT\\StockAccounting_ERP\\FileInput\\DataEngine.xlsx";
	String outputpath ="D:\\MrngBatch_OJT\\StockAccounting_ERP\\FileOutput\\HybridResults.xlsx";
	     public static WebDriver driver;
		public void startTest() throws Throwable 
		{
			String ModuleStatus ="";
			///create reference object
			ExcelFileUtil xl = new ExcelFileUtil(inputpath);
			//iterate all rows in master test cases sheet
			for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
			{
				if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
				{
					//store corresponding sheet into TCModule variable
					String TCModule =xl.getCellData("MasterTestCases", i, 1);
					//iterate all rows in TCModule sheet
					for(int j=1;j<=xl.rowCount(TCModule);j++)
					{
						String Description =xl.getCellData(TCModule, j, 0);
						String Object_Type = xl.getCellData(TCModule, j, 1);
						String Locator_Type = xl.getCellData(TCModule, j, 2);
						String Locator_Value = xl.getCellData(TCModule, j, 3);
						String TestData = xl.getCellData(TCModule, j, 4);
						try {
							if(Object_Type.equalsIgnoreCase("startBrowser"))
							{
								driver =FunctionLibrary.startBrowser();
							}
							else if(Object_Type.equalsIgnoreCase("openApplication"))
							{
								FunctionLibrary.openApplication(driver);
							}
							else if(Object_Type.equalsIgnoreCase("waitForElement"))
							{
								FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
							}
							else if(Object_Type.equalsIgnoreCase("typeAction"))
							{
								FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
							}
							else if(Object_Type.equalsIgnoreCase("clickAction"))
							{
								FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							}
							else if(Object_Type.equalsIgnoreCase("ValidateTitle"))
							{
								FunctionLibrary.ValidateTitle(driver, TestData);
							}
							else if(Object_Type.equalsIgnoreCase("closeBrowser"))
							{
								FunctionLibrary.closeBrowser(driver);
							}
							//write as pass in status cell
							xl.setCellData(TCModule, j, 5, "Pass", outputpath);
							ModuleStatus="True";
						}catch(Exception e)
						{
							System.out.println(e.getMessage());
							//write as Fail in status cell
							xl.setCellData(TCModule, j, 5, "Fail", outputpath);
							ModuleStatus="False";
						}
						 if(ModuleStatus.equalsIgnoreCase("True"))
						 {
							xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath); 
						 }
						 else
						 {
							 xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
						 }
					}
				}
				else
				{
					//which test case flag to N Write as Blocked into status cell
					xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
				}
			}
		}
}

			
	




