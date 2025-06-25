package firstPrj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

/**
 * ViewForm class 에서 '결과 출력' 버튼을 누르고 사용자에게 보여지는 class
 * JTextArea에 1~6번 문제의 결과 값 출력
 */
@SuppressWarnings("serial")
public class ResultForm extends JFrame{
	
	@SuppressWarnings("unused")
	private ViewFormEvt vfe;
    
	private String fileRute;
	
	private List<String> results;
	
    private Map<String, Integer> mapKey = new HashMap<>();
    private int requestNum;
    private int start;
    private int end;
    
    private int[] browserCount;
    
    private int successCount;
    private int failCount;
    
    private int abnormalCount;
    private int serviceCount;

    private int booksCount;
    private int errorCount;
    
	public ResultForm(ViewFormEvt vfe) {
		this.vfe=vfe;
		fileRute=vfe.getSelectedFilePath();
		
		// 각 문제의 결과를 계산하고 리스트에 저장
		results = new ArrayList<String>();
		results.add(countKey());			// 1번 문제
		results.add(countBrowser());  		// 2번 문제
		results.add(countSuccessAndFail());	// 3번 문제
		results.add(countPeakHour());		// 4번 문제
		results.add(countAbNormal());		// 5번 문제
		results.add(countBooksError());		// 6번 문제

		// 결과들을 줄바꿈으로 구분하여 텍스트에 설정
		
	} //ResultForm
	
	public List<String> getResults() {
		return results;
	} //getResults

	public void reportSave() {
		// 1. 디렉토리 생성
        File dir = new File("c:/dev/report");
        
        if (!dir.exists()) {
            dir.mkdirs(); // 디렉토리가 없으면 생성
        } //end if
        
        // 2. 파일 생성
        Date nowDate = new Date();
        SimpleDateFormat todayDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String strNowDate = todayDate.format(nowDate);
        String fileName = "report_" + strNowDate + ".dat";

		File file = new File(dir.getAbsolutePath() + File.separator + fileName);
		
		// 4. 스트림 연결
        try (FileOutputStream fos = new FileOutputStream(file)) { //이렇게하면 close()가 필요없어 

            // 5. 스트림에 데이터 기록
        	for(String str : results ) {
        	    fos.write(str.getBytes());
        	} //end for

            // 6. 명시적으로 flush
            fos.flush();
        } catch(IOException ie) {
        	ie.printStackTrace();
        } //end try catch
	} //reportSave
	
	
	// Count key occurrences from a single line
	public void countKey(String temp) {
		String key = null;
		if (temp.contains("key")) {
			key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
			mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
		} //end if
	} //countKey
	
	/**
	 * 1번 문제
	 */
	public String countKey() {
		String result=null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileRute));
			String temp;
			while ((temp = br.readLine()) != null) {
				requestNum++;
				countKey(temp);
				if (start != 0 && end != 0) {
					if (requestNum >= start && requestNum <= end) {
						countKey(temp);
					} //end 2nd if
				} //end 1st if
			} //end while
			// Call countMaxKey() after reading the log
			result=countMaxKey();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} //end try catch
		} //end try catch finally
		return result;
	} //countKey
	
    // Method to count the most frequent key in the log
    public String countMaxKey() {
        // Initialize the map to store key counts
        Map<String, Integer> keyCountMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileRute))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Extract the key from the log line
                String key = extractKeyFromLine(line);
                // Increment the count of this key in the map
                keyCountMap.put(key, keyCountMap.getOrDefault(key, 0) + 1);
            } //end while
        } catch (IOException e) {
            e.printStackTrace();
        } //end try catch

        // Find the key with the maximum count
        String maxKey = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : keyCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxKey = entry.getKey();
            } //end if
        } //end for

        // Update the JTextArea with the most frequent key
        String output = maxKey != null ? "1. 최다 사용키 : " + maxKey + " "+ maxCount + "회" 
                                      : "1. log의 키 값을 찾을 수 없습니다.";
        return output;  // Set the output in JTextArea
    } //countMaxKey

    // Helper method to extract key from the log line
    private String extractKeyFromLine(String line) {
        String key = null;
        if (line.contains("key")) {
            key = line.substring(line.indexOf("=") + 1, line.indexOf("&"));
        } //end if
        return key != null ? key : "Unknown";
    } //extractKeyFormLine

    /**
     * 2번 문제
     * 2. 브라우저별 접속횟수, 비율
     * IE - xx (xx%)
     * Chrome - xx (xx%)
     * @return
     */
    public String countBrowser() {
    	browserCount = new int[5];
    	String[] browserNames = { "[ie]", "[firefox]", "[opera]", "[Chrome]", "[Safari]" };
    	serviceCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//줄을 읽어올 때 마다 전체서비스요청 횟수 증가
            	serviceCount++;
            	//브라우저별 요청 횟수 증가
            	for(int i=0; i<browserNames.length; i++) {
            		if(line.contains(browserNames[i])) {
            			browserCount[i]++;
            		} //end if
            	} //end for
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 비정상적인 요청 횟수의 비율 계산
        float[] browserRates = new float[5];
        for(int i=0; i<browserCount.length; i++) {
        	browserRates[i]=serviceCount > 0 ? ((float)browserCount[i] / serviceCount) * 100 : 0;
        } //end for
        
        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        StringBuilder result = new StringBuilder("2. ");
        String[] browser = {"ie", "firefox", "opera", "Chrome", "Safari"};
        
        for(int i=0; i<browserCount.length; i++) {
        	result.append(browser[i]).append(" - ").append(browserCount[i]).append("(").append((int)browserRates[i]).append("%)\n");
        } //end for
        
        return result.toString();
    } //countBrowser

    /**
     * 3번 문제
     * 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
     */
    public String countSuccessAndFail() {
        successCount = 0;
        failCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//숫자 200이 포함된 줄 일 경우, 성공횟수 증가
                if (line.contains("[200]")) {
                    successCount++;
                } else if (line.contains("[404]")) {
                	//숫자 404이 포함된 줄 일 경우, 실패횟수 증가
                    failCount++;
                } //end else if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "3. 서비스를 성공한 횟수(200) : " + successCount + "회, 서비스를 실패한 횟수 (404) : " + failCount +"회";
        return result;
    } //countSuccessAndFail
    
    /**
     * 4번 - 요청이 많은 시간
     * @param logString
     * @param lineCount
     */
//     /**
   public String countPeakHour() {
       // 시간별 요청 수를 저장할 HashMap 생성 (Key: 시간, Value: 요청 횟수)
       Map<String, Integer> timeMap = new HashMap<>();

       try (BufferedReader br = new BufferedReader(new FileReader(fileRute))) {
           String line;
           while ((line = br.readLine()) != null) { // 파일의 각 줄을 읽어옴
               // 로그에서 마지막 "["와 "]" 사이의 내용을 찾음 (시간 정보가 포함된 부분)
               int startIdx = line.lastIndexOf("[") + 1; 
               int endIdx = line.lastIndexOf("]");
               
               // 유효한 시간 정보가 있는 경우 처리
               if (startIdx > 0 && endIdx > startIdx) {
                   String timestamp = line.substring(startIdx, endIdx); // "2018-04-06 09:07:34"
                   String hour = timestamp.split(" ")[1].split(":")[0]; // "09" (시간만 추출)

                   // 해당 시간대의 요청 횟수를 증가시킴
                   timeMap.put(hour, timeMap.getOrDefault(hour, 0) + 1);
               }
           }
       } catch (IOException e) {
           e.printStackTrace(); // 파일 읽기 오류 발생 시 예외 출력
       }

       // 요청이 가장 많은 시간대 찾기
       String peakHour = null;
       int maxCount = 0;
       for (Map.Entry<String, Integer> entry : timeMap.entrySet()) {
           if (entry.getValue() > maxCount) { // 요청 수가 더 많은 경우 갱신
               maxCount = entry.getValue();
               peakHour = entry.getKey();
           }
       }

       // 가장 요청이 많은 시간대 정보를 문자열로 반환
       return peakHour != null 
           ? "4. 가장 요청이 많은 시간 : [" + peakHour + "시][" + maxCount + "회]" 
           : "4. 로그에서 시간 정보를 찾을 수 없습니다.";
   }

    
    /**
     * 5번 문제
     * 비정상적인 요청(403)이 발생한 횟수, 비율구하기
     * @return
     */
    public String countAbNormal() {
    	abnormalCount = 0;
    	serviceCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//줄을 읽어올 때 마다 전체서비스요청 횟수 증가
            	serviceCount++;
            	//숫자 403이 포함된 줄 일 경우, 비정상적인 요청 횟수 증가
                if (line.contains("[403]")) {
                	abnormalCount++;
                } //end if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 비정상적인 요청 횟수의 비율 계산
        float abnormalRate = serviceCount > 0 ? ((float) abnormalCount / serviceCount) * 100 : 0;

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "5. 비정상적인 요청 횟수(403) : " + abnormalCount + "회, 비정상적인 요청 비율 : " + ((int)abnormalRate)+"%";
        return result;
    } //countAbNormal
    
    /**
     * 6번 문제
     * books 에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율 구하기
     * @return
     */
    public String countBooksError() {
    	booksCount = 0;
    	errorCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (line.contains("books")) {
                	//books 문자열이 포함된 줄일 경우, 요청 키값 증가
                	booksCount++;
                	if(line.contains("[500]")) {
                		//숫자 500이 포함된 줄일 경우, 에러 횟수 증가
                		errorCount++;
                	} //end 2nd if
                } //end 1st if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 에러 요청 횟수의 비율 계산
        float errorRate = booksCount > 0 ? ((float) errorCount / booksCount) * 100 : 0;

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "6. books URL 요청 횟수 : " + booksCount + "회, 에러 발생 횟수(500) : "+errorCount + "회 ("+((int)errorRate)+"%)";
        return result;
    } //countBooksError
	
} //class
