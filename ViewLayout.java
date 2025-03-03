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
	
	private JTextArea logTextarea;
	private JTextArea resultTextarea;
	private JButton openFileBtn;
	private JButton resultLogBtn;
	private JButton reportSaveBtn;
	
//	1. window Component 상속
	public ViewForm() {
		super("로그인 된 창");
//		2. Component 생성
		logTextarea=new JTextArea();
		logTextarea.setEditable(false); // 편집불가
		resultTextarea=new JTextArea();
		resultTextarea.setEditable(false); // 편집불가
		
		JScrollPane logScroll=new JScrollPane(logTextarea);
		logScroll.setPreferredSize(new Dimension(500, 500)); // 💡 크기 설정 추가
		
		JScrollPane resultScroll=new JScrollPane(resultTextarea);
		resultScroll.setPreferredSize(new Dimension(300, 500)); // 💡 크기 설정 추가
		
        // 2. 스크롤 패널을 감싸는 패널 생성
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(20, 50, 20, 50)); // 💡 좌우 여백 100px 추가
        centerPanel.add(logScroll, BorderLayout.CENTER);
        centerPanel.add(resultScroll, BorderLayout.EAST);
		
        //Button(버튼) Component 생성
		JPanel panelBtn=new JPanel();
		openFileBtn=new JButton("파일 열기");
		openFileBtn.setPreferredSize(new Dimension(150,40)); // 💡 크기 설정 추가
		resultLogBtn=new JButton("로그파일 분석");
		resultLogBtn.setPreferredSize(new Dimension(150,40)); // 💡 크기 설정 추가
		reportSaveBtn=new JButton("레포트 생성");
		reportSaveBtn.setPreferredSize(new Dimension(150,40)); // 💡 크기 설정 추가
		
		
		//Button(버튼) Component setBackground,setForeground(색상) 변경
		openFileBtn.setBackground(Color.lightGray);
		openFileBtn.setForeground(Color.black);
		resultLogBtn.setBackground(Color.lightGray);
		resultLogBtn.setForeground(Color.black);
		reportSaveBtn.setBackground(Color.lightGray);
		reportSaveBtn.setForeground(Color.black);
		
		panelBtn.add(openFileBtn);
		panelBtn.add(resultLogBtn);
		panelBtn.add(reportSaveBtn);
		
//		3. 배치관리자를 설정, Component 배치
		setLayout(new BorderLayout());
		add(panelBtn, BorderLayout.NORTH); // 버튼 패널 위 배치
        add(centerPanel, BorderLayout.CENTER); // 스크롤 영역 중앙 배치
		
//		로그인 Button(버튼) 이벤트
		ViewFormEvt vle=new ViewFormEvt(this);
		resultLogBtn.addActionListener(vle);
		openFileBtn.addActionListener(vle);
		reportSaveBtn.addActionListener(vle);
		
//		Layout(레이아웃) window 크기 설정
        pack(); // 💡 레이아웃에 맞게 자동 크기 조정
        setLocationRelativeTo(null); // 화면 중앙 정렬
		
//		5. 사용자에게 보여주기
		setResizable(false); // 크기조절 불가
		setVisible(true);
//		6. window 종료 이벤트 처리
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	} //ViewForm
	
	public JTextArea getLogTextarea() {
		return logTextarea;
	} //getLogTextarea

	public JTextArea getResultTextarea() {
		return resultTextarea;
	} //getResultTextarea

	public JButton getOpenFileBtn() {
		return openFileBtn;
	} //getOpenFileBtn

	public JButton getResultLogBtn() {
		return resultLogBtn;
	} //getResultLogBtn

	public JButton getReportSaveBtn() {
		return reportSaveBtn;
	} //getReportSaveBtn

	public static void main(String[] args) {
		new ViewForm();
	} //main

} //class
