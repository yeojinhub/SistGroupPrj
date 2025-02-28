package firstPrj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * LoginForm class 에서 로그인 이후에 사용자에게 보여지는 클래스
 */
@SuppressWarnings("serial")
public class ViewForm extends JFrame{
	
	private JTextArea textArea;
	private JButton openFileBtn;
	private JButton resultBtn;
	
//	1. window Component 상속
	public ViewForm() {
		super("로그인 된 창");
//		2. Component 생성
		textArea=new JTextArea();
		textArea.setEditable(false); // 편집불가
		JScrollPane scrollPane=new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500)); // 💡 크기 설정 추가
		
        // 2. 스크롤 패널을 감싸는 패널 생성
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(20, 50, 20, 50)); // 💡 좌우 여백 100px 추가
        centerPanel.add(scrollPane, BorderLayout.CENTER);
		
        //Button(버튼) Component 생성
		JPanel panelBtn=new JPanel();
		openFileBtn=new JButton("파일 열기");
		openFileBtn.setPreferredSize(new Dimension(100,40)); // 💡 크기 설정 추가
		resultBtn=new JButton("결과 출력");
		
		
		//Button(버튼) Component setBackground,setForeground(색상) 변경
		openFileBtn.setBackground(Color.lightGray);
		openFileBtn.setForeground(Color.black);
		resultBtn.setBackground(Color.lightGray);
		resultBtn.setForeground(Color.black);
		
		panelBtn.add(openFileBtn);
		panelBtn.add(resultBtn);
		
//		3. 배치관리자를 설정, Component 배치
		setLayout(new BorderLayout());
		add(panelBtn, BorderLayout.NORTH); // 버튼 패널 위 배치
        add(centerPanel, BorderLayout.CENTER); // 스크롤 영역 중앙 배치
		
//		로그인 Button(버튼) 이벤트
		ViewFormEvt vle=new ViewFormEvt(this);
		resultBtn.addActionListener(vle);
		openFileBtn.addActionListener(vle);
		
//		Layout(레이아웃) window 크기 설정
        pack(); // 💡 레이아웃에 맞게 자동 크기 조정
        setLocationRelativeTo(null); // 화면 중앙 정렬
		
//		5. 사용자에게 보여주기
		setResizable(false); // 크기조절 불가
		setVisible(true);
//		6. window 종료 이벤트 처리
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	} //ViewForm
	
	public JTextArea getJta() {
		return textArea;
	} //getJta

	public JButton getOpenFileBtn() {
		return openFileBtn;
	} //getOpenFileBtn
	
	public JButton getResultBtn() {
		return resultBtn;
	} //getResultBtn

	public static void main(String[] args) {
		new ViewForm();
	} //main

} //class
