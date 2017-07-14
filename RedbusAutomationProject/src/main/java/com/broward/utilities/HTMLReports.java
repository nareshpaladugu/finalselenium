package com.broward.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.broward.base.Base;


public class HTMLReports extends Base {

	public static int iPcount=0;
	public static int iFcount=0;
	public static int iSCount=0;
	public static int iStepNo=0;
	static int iStepPCount=0;
	static int iStepFCount=0;
	public Writer writer;
	/**
	 * @throws IOException 
	 * 
	 */
	public void summaryReport() throws IOException{
		try {

			Iterator<?> it1 = Base.TestCaseStatus.entrySet().iterator();
			while(it1.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry pair1 = (Map.Entry)it1.next();
				String[] sValue1 = Base.TestCaseStatus.get(pair1.getKey()).split(",");
				if(sValue1[1].equalsIgnoreCase("pass"))
					iPcount++;
				else if(sValue1[1].equalsIgnoreCase("fail"))
					iFcount++;
				else
					iSCount++;					
			}
			writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\SummaryReport.html",true);
			writer.write("<html><head>");
			writer.write("<table border=0 width='100%'>");
			writer.write("<tbody>");
			writer.write("<tr><th width=25%><img src='C:\\Users\\RedBusLogo\\Redbus.png' alt=REDBUS_LOGO height=100></th>");
			writer.write("<th><font color='green' size=6 face=verdana><b>SUMMARY REPORT</font></th>");
			writer.write("<th width=25%></th></tr></tbody></table></head>");
			writer.write("<div><table border=1 height='200' width='100%'>");
			writer.write("<tr height='60'><th colspan=4 bgcolor='pink'><font size=5 face='arial'><b>Execution Result</th>");
			writer.write("</tr><tr align='center'>");
			writer.write("<td width='25%'><b>Application Name</td>");
			writer.write("<td width='25%'>RedBus</td>");
			writer.write("<td rowspan='8' width='50%'>");
			writer.write("<html>");
			writer.write("<head>");
			writer.write("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
			writer.write("<script type=\"text/javascript\">"
					+"google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
					+"google.setOnLoadCallback(drawChart);"
					+"function drawChart() {"
					+" var data = google.visualization.arrayToDataTable(["
					+"['Test Cases', 'Total Executed'],"
					+"['Passed',"+iPcount+"],"
					+"['Failed',"+iFcount+"],"
					+"['Skipped',"+iSCount+"],"
					+"]);"

			        +"var options = {"
			        +"title: 'Visual Report',"
			        +"pieHole: 0.4,"
			        +"is3D: true,"
			        +"slices: {0: {color: 'green'},1:{color:'red'}, 2: {color: '#5780AE'}},"
			        +"pieSliceTextStyle: {color: 'black',},"
			        +"pieSliceText:'value',"
			        +"};"

			        +"var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
			        +"chart.draw(data, options);"
			        +"}"
			        +"</script></head><body><div id=\"piechart\" ></div></body></html></td></tr>");

			Iterator<?> it2 = Base.TestCaseStatus.entrySet().iterator();
			Long l = 0L ;
			while(it2.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it2.next();
				String[] sValue = pair.getValue().toString().split(",");

				l = l+Long.parseLong(sValue[2]);
			}

			writer.write("<tr align='center'><td><b>Executed On</td><td>"+GetDateTime.getCurrentDate("dd-MM-yyyy - hh:mm:ss aa")+"</td></tr>");
			writer.write("<tr align='center'><td><b>Machine Name</td><td>"+InetAddress.getLocalHost().getHostName()+"</td></tr>");
			writer.write("<tr align='center'><td><b>OS</td><td>"+System.getProperty("os.name")+"</td></tr>");
			//			writer.write("<tr align='center'><td><b>Environment</td><td>"+Base.sTargetEnvironment+"</td></tr>");
			writer.write("<tr align='center'><td><b>Execution time (in min)</td><td>"+GetDateTime.convertTime(l)+"</td></tr>");
			writer.write("<tr align='center'><td><b>Total Scripts</td><td>"+Base.TestCaseStatus.size()+"</td></tr></table></div>");
			writer.write("<body><table border='1' width='100%'><thead bgcolor='#5780AE'>");
			writer.write("<tr><th width='5%'>S.No</th><th width='55%'>Test Script</th><th width='15%'>Browser</th><th width='7%'>Status</th><th width='8%'>Execution Time</th></tr></thead>");
			writer.write("<tbody align='center'>");

			int iSno = 0;
			Iterator<?> it = Base.TestCaseStatus.entrySet().iterator();
			while(it.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
				String[] sValue = pair.getValue().toString().split(",");
				String sLogo = "Pass";
				if(sValue[1].equalsIgnoreCase("pass"))
					sLogo = "Pass";
				else if(sValue[1].equalsIgnoreCase("fail"))
					sLogo = "Fail";
				else
					sLogo = "Skip";
				writer.write("<tr><td align='center'>"+(++iSno)+"</td>");
				if((!(configurations.sScriptPassResult.equalsIgnoreCase("true")))&&(sLogo.equalsIgnoreCase("pass")))
					writer.write("<td>"+pair.getKey()+"</td>");
				else
					writer.write("<td align='left'><a href=.\\"+pair.getKey()+".html>"+pair.getKey()+"</td>");
				writer.write("<td align='center'>"+sValue[0]+"</td>");
				writer.write("<td align='center'><img src='C:\\Users\\RedBusLogo\\pass.png' alt='Passed' height='25' width='25'></td>");
				writer.write("<td align='center'>"+GetDateTime.convertTime(Long.parseLong(sValue[2]))+"</td></tr>");
			}
			writer.write("</tbody></table></body>");
			writer.write("<footer><table border='1' width='100%' align='center'><tfoot>");
			writer.write("<tr align='center'><td width='26%'><b>Scripts Passed</td><td><font color='green'>"+iPcount+"</font></td>");
			writer.write("<td width='26%'><b>Scripts Failed</td><td><font color='red'>"+iFcount+"</font></td>");
			writer.write("<td width='26%'><b>Scripts Skipped</td><td><font color='#5780AE'>"+iSCount+"</font></td>");
			writer.write("</tr></tfoot></tr></table></footer></html>");
			writer.close();
		} catch (Exception e) {
			writer.close();
		}
	}

	/**
	 * @throws IOException 
	 * 
	 */
	public void detailedReportHeader() throws IOException{
		try {
			iStepFCount = 0;
			iStepPCount = 0;
			iStepNo = 0;
			File f = new File(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html");
			if(f.exists())
				f.delete();
			writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html",true);
			writer.write("<html><head><table border='0' width='100%'><tbody><tr>");
			writer.write("<th width='25%'><img src='C:\\Users\\RedBusLogo\\Redbus.png' alt=REDBUS_LOGO height='100'></th>");
			writer.write("<th><font color='green' size='6' face='verdana'><b>DETAILED REPORT</font></th>");
			writer.write("<th width='25%'></th>");
			writer.write("</tr></tbody></table></head>");
			writer.write("<div><table border=1 height='100' width='100%'><thead><tr height='60'>");
			writer.write("<th colspan=6 bgcolor='pink'><font size=5 face='arial'><b>Execution Result</th>");
			writer.write("</tr></thead><tbody><tr align='center'>");
			writer.write("<td width='15%'><b>Script Name</td>");
			writer.write("<td width='25%'>"+Base.testCase+"</td>");
			writer.write("<td width='15%'><b>Browser</td>");
			writer.write("<td width='15%'>"+configurations.Browser+"</td>");
			writer.write("<td width='15%'><b>Executed Date&Time</td>");
			writer.write("<td width='15%'>"+GetDateTime.getCurrentDate("dd-MM-YYYY - hh:mm:ss aa")+"</td>");
			writer.write("</tr></tbody></table>");
			writer.write("<table border='1' width='100%'>");
			writer.write("<thead bgcolor='#5780AE'><tr>");
			writer.write("<th width='5%'>S.No</th>");
			writer.write("<th width='30%'>Step Name</th>");
			writer.write("<th width='40%'>Step Description</th>");
			writer.write("<th width='7%'>Status</th>");
			writer.write("<th width='8%'>Time(in sec)</th>");
			writer.write("</tr></thead><tbody align='center'>");
			writer.close();
		} catch (Exception e) {
			writer.close();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void detailedReportFooter(String sErrMsg) throws IOException{
		try {			
			writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html",true);
			iStepNo = 0;
			writer.write("</tbody></table>");
			writer.write("<table align='center' border='1' width='100%'>");
			writer.write("<tfoot align='center'><tr>");
			writer.write("<td width='26%'><b>Steps passed</td>");
			writer.write("<td width='7%'><font color='green'>"+iStepPCount+"</font></td>");
			writer.write("<td width='26%'><b>Steps Failed</td>");
			writer.write("<td width='7%'><font color='red'>"+iStepFCount+"</font></td>");
			writer.write("<td width='26%'><b>Execution Time (in Sec)</td>");
			writer.write("<td width='7%'>"+GetDateTime.convertTime(GetDateTime.testCaseExecutionTime())+"</td></tr>");
			writer.write("<tr>");
			writer.write("<td width='26%' colspan='6'><b>"+sErrMsg+"</td>");
			writer.write("</tr></tfoot></table></body></html>");
			writer.close();
			iStepFCount = 0;
			iStepPCount = 0;
		} catch (Exception e) {
			writer.close();
		}
	}

	/**
	 * 
	 * @param sStepName
	 * @param sStepDescription
	 * @throws IOException
	 */
	public void successReport(String sStepName, String sStepDescription) throws IOException{
		try {
			if(configurations.sScriptPassResult.equalsIgnoreCase("true"))
			{
				iStepPCount++;
				String sPath = "";
				writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html",true);
				if(configurations.sTakeScreenshot.equalsIgnoreCase("true"))
				{
					String sFileName = "";
					if(sStepName.length()<50)
						sFileName =(sStepName.concat("_"+GetDateTime.getCurrentDate("dd/mm/yyyy hh:mm:ss"))).replace(" ","_").replace(":","_").replace("/","_").replace("-","_");
					else
						sFileName =((sStepName.substring(0, 50)).concat("_"+GetDateTime.getCurrentDate("dd/mm/yyyy hh:mm:ss"))).replace(" ","_").replace(":","_").replace("/","_").replace("-","_");
					sPath = (".\\Screenshots\\").concat(sFileName);
					File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
					FileUtils.copyFile(scrFile, new File(sPath+".png"));
				}
				writer.write("<tr><td>"+(++iStepNo)+"</td>");
				writer.write("<td align='left'>"+" "+sStepName+"</a></td>");
				writer.write("<td align='left'>"+" "+sStepDescription+"</td>");
				if(configurations.sTakeScreenshot.equalsIgnoreCase("true"))
					writer.write("<td><a href="+sPath+".png><img src='C:\\Users\\RedBusLogo\\pass.png' alt='Passed' height='25' width='25'></a></td>");
				else
					writer.write("<td><img src='C:\\Users\\RedBusLogo\\pass.png' alt='Passed' height='25' width='25'></td>");
				long i = GetDateTime.stepExecutionTime();
				if(i<1)
					i=1;
				writer.write("<td>"+GetDateTime.convertTime(i)+"</td></tr>");
				writer.close();
				Base.stepStartTime = System.currentTimeMillis();
			}
			if(!(Base.TestCaseStatus.get(Base.testCase).toLowerCase().contains("fail")))
				Base.TestCaseStatus.put(Base.testCase, configurations.Browser+",Pass");

		} catch (Exception e) {
			writer.close();
		}
	}

	/**
	 * 
	 * @param sStepName
	 * @param sStepDescription
	 * @throws IOException
	 */
	public void failureReport(String sStepName, String sStepDescription) throws IOException{
		try {			
			iStepFCount++;
			writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html",true);
			String sFileName = "";
			if(sStepName.length()<50)
				sFileName =(sStepName.concat("_"+GetDateTime.getCurrentDate("dd/mm/yyyy hh:mm:ss"))).replace(" ","_").replace(":","_").replace("/","_").replace("-","_");
			else
				sFileName =((sStepName.substring(0, 50)).concat("_"+GetDateTime.getCurrentDate("dd/mm/yyyy hh:mm:ss"))).replace(" ","_").replace(":","_").replace("/","_").replace("-","_");
			String sPath = (System.getProperty("user.dir").concat("\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\Screenshots\\")).concat(sFileName);
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
			FileUtils.copyFile(scrFile, new File(sPath+".png"));

			writer.write("<tr><td>"+(++iStepNo)+"</td>");
			writer.write("<td align='left'>"+" "+sStepName+"</a></td>");
			writer.write("<td align='left'>"+" "+sStepDescription+"</td>");
			writer.write("<td><a href=.\\Screenshots\\"+sFileName+".png><img src='C:\\Users\\RedBusLogo\\fail.png' alt='Failed' height='25' width='25'></a></td>");
			long i = GetDateTime.stepExecutionTime();
			if(i<1)
				i=1;
			writer.write("<td>"+GetDateTime.convertTime(i)+"</td></tr>");
			writer.close();
			Base.TestCaseStatus.put(Base.testCase, configurations.Browser+",Fail");
			Base.stepStartTime = System.currentTimeMillis();
		} catch (Exception e) {
			writer.close();
		}
	}

	/**
	 * 
	 * @param sStepName
	 * @param sStepDescription
	 * @throws IOException
	 */
	public void SkippedReport(String sStepDescription) throws IOException{
		try {
			writer = new FileWriter(System.getProperty("user.dir")+"\\Reports\\Report_"+(Base.suiteStartDateTime).replace("/","_").replace(" ","_").replace(":","_")+"\\"+Base.testCase+".html",true);
			writer.write("<tr><td>1</td>");
			writer.write("<td colspan='2' align='center'>"+" "+sStepDescription+"</a></td>");
			writer.write("<td><img src='C:\\Users\\RedBusLogo\\fail.png' alt='Failed' height='25' width='25'></a></td>");
			long i = GetDateTime.stepExecutionTime();
			if(i<1)
				i=1;
			writer.write("<td>"+i+"</td></tr>");
			writer.close();
			Base.stepStartTime = System.currentTimeMillis();
		} catch (Exception e) {
			writer.close();
		}
	}
}
