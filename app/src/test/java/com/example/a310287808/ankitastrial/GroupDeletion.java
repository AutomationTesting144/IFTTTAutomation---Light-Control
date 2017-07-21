package com.example.a310287808.ankitastrial;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/27/2017.
 */

public class GroupDeletion {

    public MobileElement listItem;
    public int lightCounter=0;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    public MobileElement RoomListItem;

    public void GroupDeletion (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException  {

        driver.navigate().back();
        //Opening Hue application
        driver.findElement(By.xpath("//android.widget.TextView[@bounds='[24,1380][216,1572]']")).click();
        TimeUnit.SECONDS.sleep(2);
        //Clicking on settings button
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1026,184][1074,232]']")).click();
        TimeUnit.SECONDS.sleep(2);
        //Selecting Room setup
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,408][152,536]']")).click();
        TimeUnit.SECONDS.sleep(2);
        //getting the list of rooms available in the application
        driver.findElement(By.id("com.philips.lighting.hue2:id/hue_play_toolbar")).isDisplayed();

        List RoomList = driver.findElements(By.id("com.philips.lighting.hue2:id/list_item_title"));
        int Total=RoomList.size();
        int TotalRooms=Total-1;
        String DeletedRoom= ((MobileElement) RoomList.get(TotalRooms)).getText();


        int bound=192*TotalRooms;
        int bound1=196+bound;
        int bound2=316+bound;

        String FinalBound="[1080,"+bound1+"][1200,"+bound2+"]";

        //Click on the i bubble in front of room to be deleted
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='"+FinalBound+"']")).click();

        TimeUnit.SECONDS.sleep(2);

        //getting the name of the room to be deleted
        String DeletedToBeRoom = driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).getText();
        //Clicking on the DELETE button
        driver.findElement(By.id("com.philips.lighting.hue2:id/delete")).click();
        TimeUnit.SECONDS.sleep(2);
        //Clicking on confirmation  box
        driver.findElement(By.id("android:id/button1")).click();
        TimeUnit.SECONDS.sleep(2);
        //Going back from the application
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.navigate().back();
        //Clicking on Home button
        WebElement abc = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[544,1670][656,1782]']"));
        abc.click();
        //opening ifttt
        driver.findElement(By.xpath("//android.widget.TextView[@text='IFTTT']")).click();
        TimeUnit.SECONDS.sleep(5);
        //clicking on search button
        WebElement abc4 = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[407,1775][493,1809]']"));
        abc4.click();
        TimeUnit.SECONDS.sleep(5);
        //Clicking on the search text box
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).click();
        TimeUnit.SECONDS.sleep(5);
        //Entering aplication name
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).sendKeys("Press a button to make your Hue lights color loop" + "\n");
        System.out.println("Waiting for few minutes for Addition/Deletion changes to be reflect on IFTTT");
        TimeUnit.MINUTES.sleep(8);

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Press a button to make your Hue lights color loop']")));
        //Clicking on the discovered applet
        driver.findElement(By.xpath("//android.widget.TextView[@text='Press a button to make your Hue lights color loop']")).click();
        //clicking on the edit button
        driver.findElement(By.id("com.ifttt.ifttt:id/edit")).click();
        //clicking on dropdown
        driver.findElement(By.id("com.ifttt.ifttt:id/spinner_arrow")).click();
        while (driver.findElements(By.id("android:id/text1")).isEmpty()) {
            driver.findElement(By.id("com.ifttt.ifttt:id/spinner_arrow")).click();
        }
        Boolean RoomAvailable=
                ((AndroidDriver) driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\" \").instance(0))").isDisplayed();
        //Locate all drop down list elements
        if (RoomAvailable==true) {
            List dropList = driver.findElements(By.id("android:id/text1"));

            for ( int i = 0; i < dropList.size(); i++ ) {
                listItem = (MobileElement) dropList.get(i);
                Boolean result = listItem.getText().equals(DeletedToBeRoom);
                if (result == false) {
                    lightCounter++;

                } else {
                    continue;
                }
            }
            if (lightCounter == 0) {

                Status = "0";
                ActualResult = "Room: " + DeletedRoom + " is not DELETED from IFTTT";
                Comments = "FAIL: Room is not DELETED from the list";
                ExpectedResult = "Room: " + DeletedRoom + " should be DELETED from IFTTT";
                System.out.println("Result: " + DeletedRoom + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

            } else {
                Status = "1";
                ActualResult = "Room: " + DeletedRoom + " is DELETED from IFTTT";
                Comments = "NA";
                ExpectedResult = "Room: " + DeletedRoom + " should be DELETED from IFTTT";
                System.out.println("Result: " + DeletedRoom + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

            }
        }
        //Going back on home from IFTTT

        driver.navigate().back();
        driver.findElement(By.xpath("//android.widget.TextView[@bounds='[1088,64][1184,160]']")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']")).click();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }

    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sdf  = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell(0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell(1);
        r2c2.setCellValue("16");

        HSSFCell r2c3 = row2.createCell(2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell(3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell(4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell(5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell(6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();
   }

}
