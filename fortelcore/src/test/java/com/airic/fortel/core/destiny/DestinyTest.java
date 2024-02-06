package com.airic.fortel.core.destiny;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.airic.fortel.core.calendar.util.LunarUtil;
import com.airic.fortel.core.model.*;
import com.airic.fortel.core.util.Const;
import com.airic.fortel.core.util.DestinyCellCriteria;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.airic.fortel.core.calendar.util.Lunar;
import com.airic.fortel.core.destiny.config.Config;
import com.airic.fortel.core.destiny.config.Config.ConfigType;
import com.airic.fortel.core.model.Data.GroundTime;
import com.airic.fortel.core.model.Data.Sex;
import com.airic.fortel.core.model.RuntimeInfo.RuntimeCategory;

/**
 * The Class AppTest.
 */
public class DestinyTest {

	public String db_path = "http://192.168.9.103:8123/?user=root&password=1234567&database=default";

	public static String birth[][] = {
			{"m", "1981", "1", "4", "午", "me"},
			{"f", "1983", "2", "19", "亥", "张馨fang"},
			{"f", "1990", "11", "13", "午", "李qian"},
			{"f", "2006", "9", "14", "申", "b站大眼睛"},
			{"m", "1976", "12", "10", "亥", "陈xue song"},
			{"f", "2022", "8", "21", "午", "月"},
			{"f", "2015", "6", "2", "巳", "星姑娘"},
			{"m", "2008", "3", "6", "巳", "张小果"},
			{"m", "2007", "8", "4", "酉", "琪琪"},
			{"m", "1964", "5", "27", "戌", "向东"},
			{"f", "2006", "9", "26", "未", "强姑娘"},
			{"f", "2007", "7", "16", "丑", "豆豆"},
			{"m", "2022", "1", "28", "未", "李小pei"},
			{"f", "1987", "8", "15", "午", "邓鑫"},
			{"f", "1980", "9", "3", "早子", "丰c"},
			{"m", "1980", "11", "19", "酉", "许wei"},
			{"m", "1958", "3", "11", "辰", "岳fu"},
			{"m", "1981", "11", "4", "早子", "曹hai bin"},
			{"f", "1988", "1", "22", "酉", "孙tt"},
			{"f", "1964", "4", "6", "辰", "四yi"},
			{"m", "1988", "2", "6", "辰", "张xin yan"},
			{"m", "1982", "5", "28", "卯", "李云fei"},
			{"f", "2011", "1", "10", "辰", "苗"},
			{"f", "1991", "3", "29", "午", "王jin"},
			{"f", "1956", "5", "10", "戌", "ma"},
			{"f", "1994", "10", "25", "寅", "群里张yu"},
			{"f", "1995", "10", "7", "辰", "朱mm"},
			{"f", "1994", "10", "1", "卯", "宝来"},
			{"f", "1996", "4", "18", "酉", "^_^"},
	};

	public static List<Map<String, String>> huaLu = new ArrayList<>();
	public static List<Map<String, String>> huaJi = new ArrayList<>();

	public void DestinyTest() {

	}

	/**
	 * Test app.
	 */
	@DisplayName("Test create Destiny from Destiny Config")
	@Test
	public void testApp() {
		Config initConfig = new Config(ConfigType.SKY, Sex.M, 1952, 12, 15, false, GroundTime.getByName("寅").get());
		Destiny destiny = new Destiny(initConfig);


		assertNotNull(destiny);
		Map<RuntimeCategory, RuntimeInfo> runtimeInfoMap = RuntimeInfo.getRuntimeInfoMap(destiny,
				new Lunar(2017, 6, 24, false));

		RuntimeInfo tenYearRuntimeInfo = runtimeInfoMap.get(RuntimeCategory.TEN_YEAR);
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeDestinyTempleGround(), Ground.getByDisplayName("巳").get());//本步大运在哪个地支上
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_CLOTH),	Ground.getByDisplayName("卯").get()); //大祿在哪个地支上
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_CLEVER),	Ground.getByDisplayName("午").get()); //大昌在哪个地支上
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_SKILL),	Ground.getByDisplayName("申").get()); //大曲在申
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_FAILURE),	Ground.getByDisplayName("辰").get()); //大羊在辰
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HINDER),	Ground.getByDisplayName("寅").get());
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HONOR),	Ground.getByDisplayName("子").get());
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HONOR2),	Ground.getByDisplayName("申").get());
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HORSE_CHANGE),	Ground.getByDisplayName("亥").get());

		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_TREASURE),	MajorStar.MAJOR_STAR_COUNSELLOR); 	//大运四化 天机化禄
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_POWER),	MajorStar.MAJOR_STAR_LAW); 				//大运四化 天梁化权
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_POSITION),	MajorStar.MAJOR_STAR_EMPEROR); 		//大运四化 紫微化科
		Assertions.assertEquals(tenYearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_PROBLEM),	MajorStar.MAJOR_STAR_MOON);   		//大运四化 太阴化忌

		RuntimeInfo yearRuntimeInfo = runtimeInfoMap.get(RuntimeCategory.YEAR);
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeDestinyTempleGround(), Ground.getByDisplayName("酉").get());    //流年地支
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_CLOTH),	Ground.getByDisplayName("午").get());  //年禄在午
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_CLEVER),	Ground.getByDisplayName("酉").get());  //年昌在酉
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_SKILL),	Ground.getByDisplayName("巳").get());
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_FAILURE),Ground.getByDisplayName("未").get());
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HINDER),	Ground.getByDisplayName("巳").get());
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HONOR),	Ground.getByDisplayName("亥").get());
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HONOR2),	Ground.getByDisplayName("酉").get());
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeMinorStarsMap().get(MinorStar.MINOR_STAR_HORSE_CHANGE),Ground.getByDisplayName("亥").get());

		Assertions.assertEquals(yearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_TREASURE),	MajorStar.MAJOR_STAR_MOON);           //流年四化 太阴化禄
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_POWER),MajorStar.MAJOR_STAR_RECREATION);
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_POSITION),	MajorStar.MAJOR_STAR_COUNSELLOR);
		Assertions.assertEquals(yearRuntimeInfo.getRuntimeStarReactionMap().get(StarReaction.STAR_TO_PROBLEM),	MajorStar.MAJOR_STAR_MOUTH);

		System.out.println(runtimeInfoMap);

		Config destinyConfig = new Config(ConfigType.SKY, Sex.F, 1952, 12, 15, false, GroundTime.getByName("寅").get());
		destiny = new Destiny(destinyConfig);
		System.out.println(destiny.toJsonString());



		// 计算生年四化
//		boolean result = new DestinyCellCriteria(destiny, Temple.TEMPLE_DESTINY) //命宮
//				.and()
//				.meetStars(MajorStar.MAJOR_STAR_HONEST) //廉貞
//				.or()
//				.sameCellSomeStars(MinorStar.MINOR_STAR_HONOR, MinorStar.MINOR_STAR_HONOR2) //天魁或天鉞同宮
//				.notMeetStars(StarReaction.STAR_TO_PROBLEM) //不見化忌
//				.endOr()
//				.endAnd()
//				.getResult();

		// 计算生年四化
//		boolean result = new DestinyCellCriteria(destiny, Temple.TEMPLE_DESTINY) //命宮
//				.and()
//				.sameCellStars(StarReaction.STAR_TO_TREASURE, StarReaction.STAR_TO_PROBLEM) //sameCellStars即是AND關係
//				.endAnd()
//				.getResult();

	}

	@Test
	public void multi_case() {
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_0, Const.MAJOR_STAR_HONEST); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_1, Const.MAJOR_STAR_COUNSELLOR); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_2, Const.MAJOR_STAR_RECREATION); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_3, Const.MAJOR_STAR_MOON); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_4, Const.MAJOR_STAR_GREED); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_5, Const.MAJOR_STAR_MONEY); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_6, Const.MAJOR_STAR_SUN); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_7, Const.MAJOR_STAR_MOUTH); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_8, Const.MAJOR_STAR_LAW); }} );
		huaLu.add(new HashMap<String, String>() {{ put(Const.SKY_9, Const.MAJOR_STAR_SOLDIER); }} );

		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_0, Const.MAJOR_STAR_SUN); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_1, Const.MAJOR_STAR_MOON); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_2, Const.MAJOR_STAR_HONEST); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_3, Const.MAJOR_STAR_MOUTH); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_4, Const.MAJOR_STAR_COUNSELLOR); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_5, Const.MINOR_STAR_SKILL); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_6, Const.MAJOR_STAR_RECREATION); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_7, Const.MINOR_STAR_CLEVER); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_8, Const.MAJOR_STAR_MONEY); }} );
		huaJi.add(new HashMap<String, String>() {{ put(Const.SKY_9, Const.MAJOR_STAR_GREED); }} );


		for (int i = 0; i < birth.length; i++) {
			Config config = new Config(ConfigType.SKY, birth[i][0] == "m" ? Sex.M : Sex.F, Integer.parseInt(birth[i][1]),
					Integer.parseInt(birth[i][2]), Integer.parseInt(birth[i][3]), false,
					GroundTime.getByName(birth[i][4]).get());
			Destiny destiny = new Destiny(config);


//			zihua(destiny, birth[i][5]);
			ji2_lu(destiny, birth[i][5]);
//			which_place_hualu(destiny, birth[i][5]);
//			get_majorStars_every(destiny, Temple.TEMPLE_SICK.getDisplayName(), birth[i][5]);

			break;
		}


	}


	/**
	 * 生年忌转禄
	 */
	public void ji2_lu(Destiny destiny, String name){
//		List<Cell> cellList = destiny.getCells();
//		for (int i = 0; i < cellList.size(); i++) {
//			Cell cell = cellList.get(i);
//			String tianGan = cell.getSky().getDisplayName();
//			String diZhi = cell.getGround().getDisplayName();
//
//			String templeName = "";
//			for (Temple t1 : cell.getTemples()) {
//				templeName += t1.getDisplayName();
//			}
//
//
//			String majorStarsStr = "";
//			for (MajorStar t1 : cell.getMajorStars()) {
//				majorStarsStr += t1.getDisplayName();
//			}
//			majorStarsStr = majorStarsStr.equals("")? "空" : majorStarsStr;
//
//
//			String huaLuStar = getMidValue(huaLu, tianGan);//计算天干的化禄星
//			String huaLuStar_place = get_place_by_star_name(destiny, huaLuStar); //计算化禄星所在宫位
//
//
//			String huaJiStar = getMidValue(huaJi, tianGan);//计算天干的化忌星
//			String huaJiStar_place = get_place_by_star_name(destiny, huaJiStar); //计算化忌星所在宫位
//			System.out.println(tianGan + " hualu: " + huaLuStar + " " +  huaLuStar_place + " huaji: " + huaJiStar + " " + huaJiStar_place);
//
//		}

		String a1 = which_place_hualu(destiny, name);
		System.out.println(a1+ " " + destiny.getReactionStar(StarReaction.STAR_TO_TREASURE) + " hualu ");



		Map<Temple, Cell> templeToCellMap = destiny.templeToCellMap;

		System.out.println(templeToCellMap.get(Temple.TEMPLE_SICK).getSky().getDisplayName() );
//		boolean result = new DestinyCellCriteria(destiny, Temple.TEMPLE_DESTINY)
//				.and()
//				.sameCellStars(StarReaction.STAR_TO_TREASURE, StarReaction.STAR_TO_PROBLEM) //sameCellStars即是AND關係
//				.endAnd()
//				.getResult();
	}

	/**
	 * 天干四化计算，根据表格计算得来，甲廉破武阳
	 * @param destiny
	 * @param name
	 */
	public void zihua(Destiny destiny, String name){
		System.out.println("");
		System.out.print(name+ "  ");

		List<Cell> cellList = destiny.getCells();
		for (int i = 0; i < cellList.size(); i++) {
			Cell cell = cellList.get(i);
			String tianGan = cell.getSky().getDisplayName();
			String diZhi = cell.getGround().getDisplayName();

			String templeName = "";
			for (Temple t1 : cell.getTemples()) {
				templeName += t1.getDisplayName();
			}


			String majorStarsStr = "";
			for (MajorStar t1 : cell.getMajorStars()) {
				majorStarsStr += t1.getDisplayName();
			}
			majorStarsStr = majorStarsStr.equals("")? "空" : majorStarsStr;

			if ( majorStarsStr.contains(getMidValue(huaLu,tianGan))){  //生年自化禄
				System.out.print("自化lu: " + templeName + " " + majorStarsStr + " " + tianGan + " "  );
			}


			if ( majorStarsStr.contains(getMidValue(huaJi,tianGan))){  //生年自化忌
				System.out.print("  自化ji: "+templeName + " " + majorStarsStr + " " + tianGan + " "  );
			}



		}
	}

	public String getMidValue(List<Map<String, String>> map3, String key){
		String returnStr = "";
		for (Map<String, String> map : map3) {
			if (map.containsKey(key)) {
				String valueForJia = map.get(key);
				returnStr = valueForJia;
				break; // 如果只需要找到第一个匹配项就退出循环
			}
		}

		return returnStr;
	}

	/**
	 * 在哪宫化禄
	 * @param destiny
	 */
	public String which_place_hualu(Destiny destiny, String name){
		String sqlBody = "with '"+ destiny.toJsonString() + "' as xzqh1\n" +
				"\n" +
				"select gongwei from\n" +
				"( select JSONExtractArrayRaw(json, 'majorStars') AS majorStars,\n" +
				"       JSONExtractArrayRaw(json, 'temples') AS temples,\n" +
				"       replace(arrayStringConcat(temples , ''), '\"', '' ) gongwei,\n" +
				"       if( has(majorStars,  '\"' || visitParamExtractString( visitParamExtractRaw(xzqh1, 'starReactionMap') , '禄') || '\"'  ), 1, 0) hualu\n" +
				"from\n" +
				"( select  JSONExtractArrayRaw( visitParamExtractRaw(xzqh1, 'cells') ) AS arr,   arrayJoin(arr) AS json ) where hualu=1 ) ; ";
		String result = HttpRequest.post( db_path )
				.body( sqlBody )
				.execute().body();
		String stringWithoutNewline = StrUtil.replace(result, "\n", "");
//		System.out.println(stringWithoutNewline + " - " + name);

		return stringWithoutNewline;
	}

	/**
	 * 获取宫里的主星
	 * @param destiny
	 * @param place   命 身 子女 福德 .....
	 * @param name
	 */
	public String get_majorStars_every(Destiny destiny, String place, String name){
		String sqlBody = "with '"+ destiny.toJsonString() + "' as xzqh1\n" +
				"\n" +
				"select replace(arrayStringConcat(majorStars , ''), '\"', '' ) majorStars  from\n" +
				"( select JSONExtractArrayRaw(json, 'majorStars') AS majorStars,\n" +
				"       JSONExtractArrayRaw(json, 'temples') AS temples,\n" +
				"       replace(arrayStringConcat(temples , ''), '\"', '' ) gongwei\n" +
				"from\n" +
				"( select  JSONExtractArrayRaw( visitParamExtractRaw(xzqh1, 'cells') ) AS arr,   arrayJoin(arr) AS json ) where gongwei like '%" + place + "%' ) ";
		String result = HttpRequest.post( db_path )
				.body( sqlBody )
				.execute().body();
		String stringWithoutNewline = StrUtil.replace(result, "\n", "");
//		System.out.println(stringWithoutNewline + " - " + name);

		String formattedString = String.format("%-10.10s - %s", stringWithoutNewline, name);
		System.out.println(formattedString);

		return stringWithoutNewline;
	}


	/**
	 * 根据star计算在哪宫
	 * @param destiny
	 * @param star
	 * @return
	 */
	public String get_place_by_star_name(Destiny destiny, String star){
		String sqlBody = "with '"+ destiny.toJsonString() + "' as xzqh1\n" +
				"\n" +
				"select gongwei from\n" +
				"( select JSONExtractArrayRaw(json, 'majorStars') AS majorStars,\n" +
				"        replace(arrayStringConcat(majorStars , ''), '\"', '' ) majorStars2,\n" +
				"\n" +
				"        JSONExtractArrayRaw(json, 'minorStars') AS minorStars,\n" +
				"        replace(arrayStringConcat(minorStars , ''), '\"', '' ) minorStars2,\n" +
				"\n" +
				"       JSONExtractArrayRaw(json, 'temples') AS temples,\n" +
				"       replace(arrayStringConcat(temples , ''), '\"', '' ) gongwei\n" +
				"from\n" +
				"( select  JSONExtractArrayRaw( visitParamExtractRaw(xzqh1, 'cells') ) AS arr,   arrayJoin(arr) AS json ) where match(majorStars2, '"+star+"') or match(minorStars2, '"+star+"'))";
		String result = HttpRequest.post( db_path )
				.body( sqlBody )
				.execute().body();
		String stringWithoutNewline = StrUtil.replace(result, "\n", "");
//		System.out.println(stringWithoutNewline + " - " + name);


		return stringWithoutNewline;
	}



}
