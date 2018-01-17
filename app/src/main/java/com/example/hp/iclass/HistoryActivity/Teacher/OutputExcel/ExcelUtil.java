package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_AllTypes;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_Checkin;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_Late;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_PP;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_Score_Bad;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentAllCheck_Score_Good;
import com.example.hp.iclass.OBJ.StudentScoreOBJ;
import com.example.hp.iclass.OBJ.SubjectSumUpOBJ;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by spencercjh on 2018/1/2.
 * iClass
 */

public class ExcelUtil {
    //内存地址
    private static String root = Environment.getExternalStorageDirectory()
            .getPath();

    public static boolean writeExcel(Context context, SubjectSumUpOBJ subjectSumUpOBJ) throws Exception {
        ArrayList<String> StudentArray = subjectSumUpOBJ.getStudentArray();
        ArrayList<StudentScoreOBJ> Subject_Check_Info = new ArrayList<>(StudentArray.size());
        String fileName = subjectSumUpOBJ.getFilename();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && getAvailableStorage() > 1000000) {
            Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
            return false;
        }
        String[] title = {"序号", "学号", "姓名", "出勤（包含教师代签）", "缺勤", "迟到", "教师代签", "好评", "差评", "平时出勤成绩"};
        String Sheet_Title = fileName;
        String Direction_Title = "缺勤-" + subjectSumUpOBJ.getScore_uncheckin() + "分" +
                "迟到-" + subjectSumUpOBJ.getScore_late() + "分" +
                "好评+" + subjectSumUpOBJ.getScore_good() + "分" +
                "差评-" + subjectSumUpOBJ.getScore_bad() + "分" +
                "总分：" + subjectSumUpOBJ.getTotal_points() + "分";
        File file;
        File dir = new File(context.getExternalFilesDir(null).getPath());
        file = new File(dir, fileName + ".xls");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 创建Excel工作表
        WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        // 添加第一个工作表并设置第一个Sheet的名字
        WritableSheet sheet = wwb.createSheet(fileName, 0);
        Label label = new Label(0, 0, Sheet_Title, getHeader()); //添加表的大标题
        sheet.addCell(label);
        label=new Label(0,1,Direction_Title,getHeader());
        sheet.addCell(label);
        for (int i = 0; i < title.length; i++) {
            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
            // 在Label对象的子对象中指明单元格的位置和内容
            label = new Label(i, 2, title[i], getHeader());
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
        }
        for (int i = 0; i < StudentArray.size(); i++) {
            String student_id = StudentArray.get(i);
            StudentScoreOBJ student_score = init_Subject_Check_Info(i, student_id, Subject_Check_Info, subjectSumUpOBJ);
            Label Lindex = new Label(0, i + 3, String.valueOf(i + 1));
            Label Lstudent_id = new Label(1, i + 3, student_score.getStudent_id());
            Label Lstudent_name = new Label(2, i + 3, student_score.getStudent_name());
            Label Lnum_checkin = new Label(3, i + 3, String.valueOf(student_score.getNum_checkin() + student_score.getNum_pp()));  //出勤==出勤+教师代签
            Label Lnum_uncheckin = new Label(4, i + 3, String.valueOf(student_score.getNum_uncheck_in()));
            Label Lnum_late = new Label(5, i + 3, String.valueOf(student_score.getNum_late()));
            Label Lnum_pp = new Label(6, i + 3, String.valueOf(student_score.getNum_pp()));
            Label Lscore_good = new Label(7, i + 3, String.valueOf(student_score.getNum_good()));
            Label Lscore_bad = new Label(8, i + 3, String.valueOf(student_score.getNum_bad()));
            Label Lclass_score = new Label(9, i + 3, String.valueOf(student_score.getClass_score()));
            sheet.addCell(Lindex);
            sheet.addCell(Lstudent_id);
            sheet.addCell(Lstudent_name);
            sheet.addCell(Lnum_checkin);
            sheet.addCell(Lnum_uncheckin);
            sheet.addCell(Lnum_late);
            sheet.addCell(Lnum_pp);
            sheet.addCell(Lscore_good);
            sheet.addCell(Lscore_bad);
            sheet.addCell(Lclass_score);
//            Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();
        }
        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();
        return true;
    }

    public static WritableCellFormat getHeader() {
        WritableFont font = new WritableFont(WritableFont.TIMES, 15,
                WritableFont.BOLD);// 定义字体
        try {
            font.setColour(Colour.BLACK);// 蓝色字体
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
            // format.setBorder(Border.ALL, BorderLineStyle.THIN,
            // Colour.BLACK);// 黑色边框
            // format.setBackground(Colour.YELLOW);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取SD可用容量
     */
    private static long getAvailableStorage() {

        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }

    static StudentScoreOBJ init_Subject_Check_Info(int i, String student_id, ArrayList<StudentScoreOBJ> ScoreArray, SubjectSumUpOBJ subjectSumUpOBJ) throws InterruptedException {
        String subject_id = subjectSumUpOBJ.getSubject_id();
        String student_name = Fun_GetStudentName.http_GetStudentName(student_id);  //学生姓名
        int num_check_in = Integer.parseInt(  //出勤（不包含教师代签）
                Fun_CountOneStudentAllCheck_Checkin.http_CountOneStudentAllCheck_Checkin(subject_id, student_id));
        int num_uncheckin = Fun_QuarySubjectTh.http_QuarySubjectTh(subject_id) -  //缺勤  节数=所有课时数 课时数-出勤（任何形式的）=缺勤
                Integer.parseInt(Fun_CountOneStudentAllCheck_AllTypes.http_CountOneStudentAllCheck_AllTypes(subject_id, student_id));
        int num_late = Integer.parseInt( //迟到
                Fun_CountOneStudentAllCheck_Late.http_CountOneStudentAllCheck_Late(subject_id, student_id));
        int num_pp = Integer.parseInt(   //教师代签
                Fun_CountOneStudentAllCheck_PP.http_CountOneStudentAllCheck_PP(subject_id, student_id));
        int num_good = Integer.parseInt( //好评
                Fun_CountOneStudentAllCheck_Score_Good.http_CountOneStudentAllCheck_Score_Good(subject_id, student_id));
        int num_bad = Integer.parseInt(  //差评
                Fun_CountOneStudentAllCheck_Score_Bad.http_CountOneStudentAllCheck_Score_Bad(subject_id, student_id));
        int class_score = subjectSumUpOBJ.getTotal_points() - //总分
                subjectSumUpOBJ.getScore_uncheckin() * num_uncheckin - //缺勤扣分
                subjectSumUpOBJ.getScore_late() * num_late +    //迟到扣分
                subjectSumUpOBJ.getScore_good() * num_good -    //好评加分
                subjectSumUpOBJ.getScore_bad() * num_bad;     //差评扣分
        if (class_score > subjectSumUpOBJ.getTotal_points()) {   //分数上溢出，恢复为满分
            class_score = subjectSumUpOBJ.getTotal_points();
        }
        if (class_score < 0) {   //分数下溢出，恢复为0分
            class_score = 0;
        }
        StudentScoreOBJ studentScoreOBJ = new StudentScoreOBJ(student_id, student_name, num_check_in, num_uncheckin, num_late, num_pp, num_good, num_bad, class_score);
        ScoreArray.add(studentScoreOBJ);
        return studentScoreOBJ;
    }
}
