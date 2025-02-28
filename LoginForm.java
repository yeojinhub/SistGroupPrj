package firstPrj;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


/**
 * public RunMainWindow class : LoginForm window 실행하기 위한 전처리용 class
 * public class LoginForm : 로그인 form 구현
 * public class LoginFormEvt : 로그인 실행하는 이벤트 class
 * JPanel jp : Component 배치하는 GridLayout 형식의 JPanel
 * JLabel idLabel : 사용자 아이디 라벨이름표
 * JTextField idText : 사용자로부터 아이디 입력받음
 * JLabel passLabel : 사용자 비밀번호 라벨이름표
 * JPasswordField passText : 사용자로부터 비밀번호 입력받음
 * JButton loginBtn : 버튼을 누르면 로그인
 */
@SuppressWarnings("serial")
public class LoginForm extends JFrame{
	
	private JTextField idText;
	private JPasswordField passText;
	private JButton loginBtn;
	
	public LoginForm() {
		super("로그인");
		
		//Layout(레이아웃) 설정 및 JPanel(판넬) 생성
		JPanel LayoutJPanel = new JPanel();
		LayoutJPanel.setBorder(new LineBorder(Color.BLUE));
		
//		Component 선언 및 생성
		
		//글씨체 설정
		Font fontIdPassLabel=new Font("맑은 고딕",Font.BOLD,23);// 라벨 : 아이디 비밀번호
		Font fontIDPassField =new Font("Console",Font.PLAIN,20);//필드 : 아이디 비밀번호
		
		//Logo (로고) Component 생성 및 글씨체 설정
		JLabel jlbLg=new JLabel("Login");
		Font fontLg=new Font("Lucida Console",Font.BOLD,40); // 로고 글씨
		
		jlbLg.setFont(fontLg);
		jlbLg.setForeground(Color.blue);
		
		//ID(아이디) Component 생성 및 글씨체 설정
		JLabel idLabel=new JLabel("아이디");
		idText=new JTextField(10);
		idLabel.setFont(fontIdPassLabel);
		idText.setFont(fontIDPassField);
		
		//Password(비밀번호) Component 생성 및 글씨체 설정
		JLabel passLabel=new JLabel("비밀번호");
		passText=new JPasswordField();
		passLabel.setFont(fontIdPassLabel);
		passText.setFont(fontIDPassField);
		
		loginBtn=new JButton("로그인");
		loginBtn.setBackground(Color.blue);
		loginBtn.setForeground(Color.white);
		
//		Layout 설정
		setLayout(null);
		LayoutJPanel.setBounds(getX()+20,getY()+70,345,180);
		jlbLg.setBounds(getX()+130,getY()-15,200,100);
		idLabel.setBounds(getX()+40,getY()+60,100,100);
		idText.setBounds(getX()+150,getY()+90,200,40);
		passLabel.setBounds(getX()+40,getY()+110,120,100);
		passText.setBounds(getX()+150,getY()+140,200,40);
		loginBtn.setBounds(getX()+40,getY()+190,310,40);
		
//		Component 배치
		add(jlbLg);
		add(idLabel);
		add(idText);
		add(passLabel);
		add(passText);
		add(passText);
		add(loginBtn);
		add(LayoutJPanel);
		
//		로그인 Button 이벤트
		LoginFormEvt lfe=new LoginFormEvt(this);
		loginBtn.addActionListener(lfe);
		
//		window 크기 설정
		setBounds(50,50,400,300);
//		사용자에게 window 출력
		setVisible(true);
	} //LoginForm
	 

	public JTextField getIdText() {
		return idText;
	} //getIdText

	public JPasswordField getPassText() {
		return passText;
	} //getPassText

	public JButton getLoginBtn() {
		return loginBtn;
	} //getLoginBtn

} //class
