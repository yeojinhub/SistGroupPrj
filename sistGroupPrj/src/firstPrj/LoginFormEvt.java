package firstPrj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * public RunMainWindow class : LoginForm window 실행하기 위한 전처리용 class
 * public class LoginForm : 로그인 레이아웃 form 구현 class
 * public class LoginFormEvt : '로그인' 버튼 액션 이벤트 구현 class
 * public class ViewForm : 로그인 이후 레이아웃 form 구현 class
 * public class ViewFormEvt : '파일 열기', '로그파일 분석', '레포트 생성' 버튼 액션 이벤트 구현 class
 * public class ResultForm : 1~8번까지의 문제 method와 결과 값 저장하는 class
 * 
 * private Map<String, String> mapLoginData : 로그인에 사용되는 아이디, 비밀번호 데이터 셋
 * private JTextField idText : LoginForm class에서 사용자로부터 아이디 입력받음
 * private JPasswordField passText : LoginForm class에서 사용자로부터 비밀번호 입력받음
 * private JButton loginBtn : LoginForm class에서 버튼을 누르면 로그인, 액션 이벤트 구현
 */
public class LoginFormEvt extends WindowAdapter implements ActionListener {

	private LoginForm lf;
	private Map<String, String> mapLoginData;
	
	private JTextField idText;
	private JPasswordField passText;
	private JButton loginBtn;
	
	public LoginFormEvt(LoginForm lf) {
		//필요한 컴포넌트를 인스턴스 변수(사용할 컴포넌트)에 할당
		this.lf=lf;
		this.idText=lf.getIdText();
		this.passText=lf.getPassText();
		this.loginBtn=lf.getLoginBtn();
		
		//로그인에 사용되는 데이터 셋
		mapLoginData=new HashMap<String, String>();
		mapLoginData.put("admin", "1234");
		mapLoginData.put("root", "1111");
		mapLoginData.put("administrator", "12345");
		
		//idText Enter Key Event 추가
		idText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtn.doClick(); // 로그인 버튼 클릭과 동일한 동작 수행
				}
			}
		});

		//passText Enter Key Event 추가
		passText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBtn.doClick(); // 로그인 버튼 클릭과 동일한 동작 수행
				}
			}
		});
	} //LoginFormEvt
	
	/**
	 * window 창을 닫았을 때 종료되는 event
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		lf.dispose();
	} //windowClosing
	
	/**
	 * '로그인' 버튼이 선택되었을때 실행하는 ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		//'로그인' 버튼이 선택되었을때 호출
		if(ae.getSource()==loginBtn) {
			System.out.println("로그인 버튼 실행");
			//아이디 입력되어있는지 확인하는 method
			idChecked();
			//비밀번호 입력되어있는지 확인하는 method
			passChecked();
			
		} //end if

	} //actionPerformed
	
	/**
	 * idChecked() : 사용자로부터 입력받은 아이디 데이터 값의 유효성을 검사하는 method
	 * 입력받은 idText 값을 String id 에 앞뒤 공백을 제거하여 저장.
	 * ID가 비었는지 isEmpty()로 확인하여 boolean flag 에 값 반환
	 * ID가 비었을 때 1. JOptionPane 경고 팝업창 출력,
	 * 2. 마우스 커서를 ID 입력 컵포넌트로 이동.
	 * ID가 제대로 입력되었을 때 1. 마우스 커서를 JPasswordField passText로 이동
	 * @return flag boolean 형식으로 true or false 값 반환
	 */
	private boolean idChecked() {
//		앞뒤 공백을 제거하여 아이디를 저장
		String id=idText.getText().trim();
		
//		ID가 비었는지 확인
		boolean flag=!id.isEmpty();
//		ID가 입력되었을 때
		if(flag) {
//			1. 마우스 커서를 JPasswordField passText로 이동
			passText.requestFocus();
		}
//		ID가 입력되지 않았을 때
		else {
//			1. JOptionPane 경고 팝업창 출력
			JOptionPane.showMessageDialog(null, "아이디는 필수입력입니다.", "로그인", JOptionPane.WARNING_MESSAGE);
//			2. 마우스 커서를 ID 입력 컵포넌트로 이동
			idText.requestFocus();
		} //end if
		return flag;
	} //idChecked
	
	/**
	 * passChecked() : 사용자로부터 입력받은 비밀번호 데이터 값의 유효성을 검사하는 method
	 * ID가 비었는지 isEmpty()로 확인하여 boolean flag 에 값 반환
	 * flag 값에 따라 ID가 입력받았을때만 passChecked method 실행
	 * ID가 비었을 때 1. JOptionPane 경고 팝업창 출력,
	 * 2. 마우스 커서를 ID 입력 컵포넌트로 이동.
	 * ID가 제대로 입력되었을 때 1. 마우스 커서를 JPasswordField passText로 이동
	 */
	private void passChecked() {
		//ID가 있는 경우에만 비밀번호 체크
		if(!idChecked()) {
			//idChecked method 호출한 곳으로 돌아가기
			return;
		} //end if
		//char[] 을 하나의 문자열로 만들어서 저장 -> String 에서 제공하는 모든 기능 사용
		String pass=new String(passText.getPassword());
		
		//비밀번호가 입력되었는지 확인하고 미입력시 경고메세지 출력
		if(pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "비밀번호는 필수 입력입니다.", "로그인", JOptionPane.WARNING_MESSAGE);
			passText.requestFocus();
			return;
		} //end if
		 
		String id=idText.getText();
		String msg="아이디나 비밀번호 확인";
		
		//비밀번호가 있으면 로그인 수행
		if(mapLoginData.containsKey(id) && mapLoginData.get(id).equals(pass)) {
			msg=("로그인 성공");
			 lf.dispose(); // 로그인 폼 닫기
			new ViewForm();
		} //end if
		JOptionPane.showMessageDialog(null, msg, "로그인", JOptionPane.WARNING_MESSAGE);
		
	} //passChecked
	
} //class
