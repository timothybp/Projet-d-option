package controllers;

import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.LinkedList;  
import java.util.List;  
  
public class mAn {
	private List<String> listResult;
	
	public mAn(){
		listResult = new ArrayList<String>();
	}

	public static void main(String[] args) {  
        String[] n ={"1","2","3","4"};  
        mAn m=new mAn();  
        List lst = Arrays.asList(n);  
        m.take("",2,lst);
        for(int i=0; i< m.listResult.size();i++)
        	System.out.println(m.listResult.get(i));
    }  
  
    public void take(String s, int total, List lst) {  
        for (int i = 0; i < lst.size(); i++) {  
            //System.out.println("i="+i);  
            List templst=new LinkedList(lst);  
            String n =  (String) templst.remove(i);// ȡ����������
            String str = "";
            if(!s.equals(""))
            	str = s + ";" + n;  
            else
            	str = s + n;  
            if (total == 1) {  
                listResult.add(str);//����� n������ֻȡһ����ֱ�Ӱ�ȡ�����Ľ���������  
              
            } else {  
                int temp=total-1;//��ͬһ����total�������ܼ�,������ԭ�б����Ļ�����  
                take(str, temp, templst);// �����temp�Լ�templst����ȫ�µı����ͼ���  
            }  
        }  
          
    }  
  
}  