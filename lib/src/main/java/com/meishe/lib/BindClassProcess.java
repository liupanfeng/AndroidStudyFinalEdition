package com.meishe.lib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


/**
 *
 * 只要成为 AbstractProcessor的子类，就已经是注解处理器了
 * @author ms
 */

/**
 * Google的这个AutoService可以去生成配置文件
 */
@AutoService(Processor.class)
/**
 * 配置版本（Java编译时的版本）
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
/**
 * 允许注解处理器  可以去处理的注解，不是所有的注解处理器都可以去处理
 */
@SupportedAnnotationTypes({"com.meishe.annotation.BindClass"})
/**
 *  注解处理器能够接收的参数（例如：如果想把AndroidApp信息传递到这个注解处理器(Java工程)，是没法实现的，所以需要通过这个才能接收到）
 */
@SupportedOptions("value")
public class BindClassProcess extends AbstractProcessor {

    /**
     * 注解节点
     */
    Elements mElementTool;

    /**
     * 类信息
     */
    Types mTypesTool;

    /**
     * 专用日志
     */
    Messager mMessager;

    /**
     * 过滤器
     */
    Filer mFiler;

    /**
     * 用于做一些初始化的操作
     * @param processingEnv
     */

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementTool =processingEnv.getElementUtils();
        mTypesTool =processingEnv.getTypeUtils();
        mMessager =processingEnv.getMessager();
        mFiler =processingEnv.getFiler();
        String value = processingEnv.getOptions().get("value");
        mMessager.printMessage(Diagnostic.Kind.NOTE,"init---->从Android App Gradle中传递过来的value="+value);
    }


    /**
     * 只要使用了定义好的注解，可以统一的执行一定的代码逻辑
     * @param annotations 使用注解的节点数组
     * @param roundEnv 封装的环境内容，使用注解的类的包名字 类名等信息都可以在这个上面拿到
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        String test = "process----------" + annotations.size();
//        mMessager.printMessage(Diagnostic.Kind.NOTE, test);


        //使用拼接的方式 注入代码
//        for (TypeElement element : annotations) {
//            // 类节点之上，就是包节点
//            String qualifiedName = mElementTool.getPackageOf(element).getQualifiedName().toString();
//            // 获取类的 简单类名
//            String simpleName = element.getSimpleName().toString();
//
//            mMessager.printMessage(Diagnostic.Kind.NOTE, "使用到注解的包节点-----" + qualifiedName + "类名-----" + simpleName);
//
//
//            String finalCreateClassName = simpleName + "###" + "BindView";
//
//            try {
//                JavaFileObject sourceFile = mFiler.createSourceFile(qualifiedName + "." + finalCreateClassName);
//
//                Writer writer = sourceFile.openWriter();
//
//                writer.write("public class " + finalCreateClassName + " {\r\n\r\n");
//
//                writer.write("public static void main(String[] args) {\r\n");
//
//                writer.write(" System.out.println(\"1 test code study....\");");
//
//                writer.write("}");
//
//                writer.write("}");
//
//                writer.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        for (TypeElement element : annotations) {
            String packageName = mElementTool.getPackageOf(element).getQualifiedName().toString();
            // 获取简单的 类名
            String className = element.getSimpleName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "packageName---"+packageName+"  className---"+className);
            // 打印一下信息

            // 最终要生成的类名
            String finalResultClassNmae = className + "$BinderViewClass";
            mMessager.printMessage(Diagnostic.Kind.NOTE,"finalResultClassNmae----"+finalResultClassNmae);


            // 开始真正的使用JavaPoet的方式来生成 Java代码文件
            MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "刘恬希 is my daughter")
                    .build();

            TypeSpec typeSpec = TypeSpec.classBuilder(finalResultClassNmae)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpec)
                    .build();

            JavaFile javaFile=JavaFile.builder(packageName,typeSpec).build();

            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                mMessager.printMessage(Diagnostic.Kind.NOTE,e.getMessage());
            }
            mMessager.printMessage(Diagnostic.Kind.NOTE, "代码注入完成...");
        }

        return true;
    }
}
