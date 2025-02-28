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
 * 흐름 Layout 과 Component 사용
 */
@SuppressWarnings("serial")
public class ViewLayout extends JFrame{
	
	private JTextArea textArea;
	private JButton openFileBtn;
	private JButton resultBtn;
	private JButton mostKeyBtn;
	private JButton successOrFailBtn;
	
//	1. window Component 상속
	public ViewLayout() {
		super("로그인 된 창");
//		2. Component 생성
		textArea=new JTextArea();
		textArea.setEditable(false); // 편집불가
		JScrollPane scrollPane=new JScrollPane(textArea);
//		scrollPane.setBounds(50, 100, 510, 400);
		scrollPane.setPreferredSize(new Dimension(500, 500)); // 💡 크기 설정 추가
		
        // 2. 스크롤 패널을 감싸는 패널 생성 (크기 고정을 위해)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(20, 50, 20, 50)); // 💡 좌우 여백 100px 추가
        centerPanel.add(scrollPane, BorderLayout.CENTER);
		
        //Button(버튼) Component 생성
		JPanel panelBtn=new JPanel();
		openFileBtn=new JButton("파일 열기");
		openFileBtn.setPreferredSize(new Dimension(100,40)); // 💡 크기 설정 추가
		resultBtn=new JButton("결과 출력");
		resultBtn.setPreferredSize(new Dimension(100,40)); // 💡 크기 설정 추가
		
		JPanel panelBtnSouth=new JPanel();
		mostKeyBtn=new JButton("최다 사용 추출");
		mostKeyBtn.setPreferredSize(new Dimension(310,40)); // 💡 크기 설정 추가
		successOrFailBtn = new JButton("서비스 횟수 추출");
		successOrFailBtn.setPreferredSize(new Dimension(310,40)); // 💡 크기 설정 추가
		
		//Button(버튼) Component setBackground,setForeground(색상) 변경
		openFileBtn.setBackground(Color.lightGray);
		openFileBtn.setForeground(Color.black);
		resultBtn.setBackground(Color.lightGray);
		resultBtn.setForeground(Color.black);
		mostKeyBtn.setBackground(Color.lightGray);
		mostKeyBtn.setForeground(Color.black);
		successOrFailBtn.setBackground(Color.lightGray);
		successOrFailBtn.setForeground(Color.black);
		
		panelBtn.add(openFileBtn);
		panelBtn.add(resultBtn);
		panelBtnSouth.add(mostKeyBtn);
		panelBtnSouth.add(successOrFailBtn);
		
//		3. 배치관리자를 설정, Component 배치
		setLayout(new BorderLayout());
//		add(scrollPane);
//		add("East", panelBtn);
		add(panelBtn, BorderLayout.NORTH); // 버튼 패널 위 배치
        add(centerPanel, BorderLayout.CENTER); // 스크롤 영역 중앙 배치
        add(panelBtnSouth, BorderLayout.SOUTH); // 버튼 패널 아래 배치
		
//		로그인 Button 이벤트
		ViewLayoutEvt vle=new ViewLayoutEvt(this);
		openFileBtn.addActionListener(vle);
		mostKeyBtn.addActionListener(vle);
		successOrFailBtn.addActionListener(vle);
		
//		Layout(레이아웃) 설정
//		setLayout(null);
//		4. window 크기 설정
//		setBounds(100, 100, 1200, 600);
//		setSize(630, 600); // 💡 크기 설정 변경 (setBounds 대신 사용)
        pack(); // 💡 레이아웃에 맞게 자동 크기 조정
        setLocationRelativeTo(null); // 화면 중앙 정렬
		
//		5. 사용자에게 보여주기
		setResizable(false); // 크기조절 불가
		setVisible(true);
//		6. window 종료 이벤트 처리
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	} //UseFlowLayout
	
	public JTextArea getJta() {
		return textArea;
	} //getJta

	public JButton getOpenFileBtn() {
		return openFileBtn;
	} //getOpenFileBtn
	
	public JButton getResultBtn() {
		return resultBtn;
	} //getResultBtn

	public JButton getMostKeyBtn() {
		return mostKeyBtn;
	} //getMostKeyBtn

	public JButton getSuccessOrFailBtn() {
		return successOrFailBtn;
	} //getSuccessOrFailBtn

	public static void main(String[] args) {
		new ViewLayout();
	} //main

} //class
