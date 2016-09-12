package com.persistentbit.robjects.js;

import com.persistentbit.core.Tuple2;
import com.persistentbit.jjson.mapping.JJMapper;
import com.persistentbit.jjson.nodes.JJPrinter;
import com.persistentbit.robjects.js.examples.Name;
import com.persistentbit.robjects.js.examples.ValueWithCollections;
import com.persistentbit.robjects.js.examples.ValueWithGen;
import com.persistentbit.sourcegen.SourceGen;
import org.junit.Test;

/**
 * @author Peter Muys
 * @since 7/09/2016
 */
public class TestCodeGenRuns {
    private final JSCodeGenSettings settings = new JSCodeGenSettings(JSCodeGenSettings.ModuleType.none, JSCodeGenSettings.CodeType.js5);

    @Test
    public void testValueClasses() {
        JavascriptTester rt = new JavascriptTester();

        CodeGenValueClass cgv = new CodeGenValueClass(settings);
        SourceGen helpers = CodeGenHelperClass.create(settings);
        rt.add(helpers.writeToString());
        rt.add(cgv.generate(Tuple2.class).writeToString());
        rt.add(cgv.generate(Name.class).writeToString());
        rt.add(cgv.generate(ValueWithGen.class).writeToString());
        rt.add(cgv.generate(ValueWithCollections.class).writeToString());
        rt.addResources("/RunValueWithGenCode.js");
        Name aVal = new Name("fn","ln");
        ValueWithGen<Name,String> val = new ValueWithGen<>(aVal,new Tuple2<>(aVal,"txt"),new Tuple2<>(1234,new Tuple2<>(aVal,"BVal")),"JustAString");
        String valJson = JJPrinter.print(true,new JJMapper().write(val));
        System.out.println("json:" + valJson);
        rt.addVar("jsonNameString",valJson);
        //rt.add("print('js-Done');");
        rt.dump();
        rt.run();
    }
    static public void main(String...args){
        new TestCodeGenRuns().testValueClasses();
    }
}
