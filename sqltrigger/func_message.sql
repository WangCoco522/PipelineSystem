CREATE OR REPLACE FUNCTION "public"."func_message"()
  RETURNS "pg_catalog"."trigger" AS $BODY$

	DECLARE 
		str VARCHAR;
		str_ts VARCHAR:=NEW.ts;
		str_device VARCHAR;
		str_name VARCHAR;
		str_message DECIMAL:=NEW.dbl_v;
		str_message2 VARCHAR:=NEW.str_v;
		str_message3 VARCHAR:=NEW.long_v;
		str_id INTEGER;
	BEGIN
	
	IF NEW.key is not NULL THEN
		SELECT ts_kv_dictionary.key into str from ts_kv,ts_kv_dictionary where ts_kv_dictionary.key_id = NEW.key;
	ELSE
		RETURN NEW;
	END IF;
	
	str_device := SUBSTRING(str,1,12);
	str_name :=SUBSTRING(str,14);

	--entity_id过滤

	IF NEW.entity_id in ('0b4ba110-85c8-11e9-a2f1-1feb32d3072b') THEN  
		
		IF (str_name ='事件代码') THEN
			UPDATE forweb.gas_event SET  eventidkey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='事件时间') THEN
			UPDATE forweb.gas_event SET  eventtimekey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name='事件-累积量') THEN
			UPDATE forweb.gas_event SET  eventcontentkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;
		RETURN NEW;
	END IF;

	IF NEW.entity_id in ('34b61960-85a8-11e9-a2f1-1feb32d3072b') THEN  
	-- 告警表数据更新
		IF (str_name='告警代码') THEN
			UPDATE forweb.gas_warning SET  warningidkey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;
		IF (str_name='告警时间') THEN
			UPDATE forweb.gas_warning SET  warningtimekey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name='告警-累积量') THEN
			UPDATE forweb.gas_warning SET  warningcontentkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		RETURN NEW;
	END IF;


	IF NEW.entity_id in ('fbd4d300-8126-11e9-a2f1-1feb32d3072b') THEN  

	-- 	消息表数据更新
		IF (str_name='信号强度') THEN
			UPDATE forweb.gas_message SET  rssikey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;
		
		IF (str_name='工况体积') THEN
			UPDATE forweb.gas_message SET  realvolkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			IF (SUBSTRING(str,1,2)='00') THEN   ----判断表的类型是否为皮膜，若是则更新最新抄码
				UPDATE forweb.gas_attribute SET latestcode=str_message WHERE deviceidkey=SUBSTRING(str,1,12);
			END IF;
		END IF;	
		
		IF (str_name='通信电池电压') THEN
			UPDATE forweb.gas_message SET  materv1key=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		
		IF (str_name ='仪表电压') THEN
			UPDATE forweb.gas_message SET  materv1key=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		
		IF (str_name='基表电池电压') THEN
			UPDATE forweb.gas_message SET  materv2key=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		
		IF (str_name ='温度') THEN
			UPDATE forweb.gas_message SET  temperaturekey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		
		IF (str_name IN('表具工作状态字','状态字')) THEN
		  
			--INSERT INTO temp(i,str)values(2,cast(str_message3 as int)::bit(8));
			
			IF(cast(str_message3 as int) > 255) THEN
				UPDATE forweb.gas_message SET  statuskey=cast(str_message3 as int)::bit(16) WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			ELSE 
				UPDATE forweb.gas_message SET  statuskey=cast(str_message3 as int)::bit(8) WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			END IF;
			
		END IF;	
		IF (str_name ='表内工作状态字') THEN
			
			UPDATE forweb.gas_message SET  meterstatuswordkey=cast(str_message3 as int)::bit(8) WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			--INSERT INTO temp(i,str)values(3,cast(str_message3 as int)::bit(8));
		END IF;	
		IF (str_name ='标况累计量') THEN
			UPDATE forweb.gas_message SET  totalstandardflowkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			IF (SUBSTRING(str,1,2)='03') THEN  --判断表的类型是否为超声波，若是则更新最新抄码
				UPDATE forweb.gas_attribute SET latestcode=str_message WHERE deviceidkey=SUBSTRING(str,1,12);
			END IF;
		END IF;	
		IF (str_name ='工况累计量') THEN
			UPDATE forweb.gas_message SET  totalrealflowkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			IF (SUBSTRING(str,1,2)='01') THEN  --判断表的类型是否为修正仪，若是则更新最新抄码
				UPDATE forweb.gas_attribute SET latestcode=str_message WHERE deviceidkey=SUBSTRING(str,1,12);
			END IF;
		END IF;	
		IF (str_name ='标况瞬时量') THEN
			UPDATE forweb.gas_message SET  standardflowkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='工况瞬时量') THEN
			UPDATE forweb.gas_message SET  realflowkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='压力值') THEN
			UPDATE forweb.gas_message SET  pressurekey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;
		IF (str_name ='时段峰值量') THEN
			UPDATE forweb.gas_message SET  peakflowkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='受扰累计体积') THEN
			UPDATE forweb.gas_message SET  interferevolkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='校正系数') THEN
			UPDATE forweb.gas_message SET  compressfactorkey=str_message WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='写入ip') THEN
			UPDATE forweb.gas_message SET  ip=str_message2 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='writeTime') THEN
			UPDATE forweb.gas_message SET  writetime=str_message2 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
			UPDATE forweb.gas_attribute SET collecttimekey=str_message2 WHERE deviceidkey=SUBSTRING(str,1,12);
		END IF;	
		IF (str_name ='采集时间') THEN
			UPDATE forweb.gas_message SET  collecttimekey=str_message3 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;	
		IF (str_name ='收到有效数据') THEN
			UPDATE forweb.gas_message SET  validdata=str_message2 WHERE deviceidkey=SUBSTRING(str,1,12) AND ts=str_ts;
		END IF;

	
------------------------------------------------------	
--   测试数据
--   IF (NEW.key='45698') THEN
--	UPDATE forweb.gas_event
--		SET
--    ts='12349'
--  	WHERE	eventtimekey='2016061620';	
--		END IF;
------------------------------------------------------	 
	 
	RETURN NEW;


-- 设备号为entity_id='1e981c4efe583c0a2f11feb32d3072b'的表端数据插入

  --ELSE
	/*
  IF NEW.entity_id='efe583c0-81c4-11e9-a2f1-1feb32d3072b' THEN  -- 
-- 向事件表中插入数据，暂时只差入设备ip和sign	
-- gas_dataanalysyslog数据更新		

		UPDATE forweb.gas_data_analy_sys_log1
		SET
		 gas_key=NEW.key,
		 --gas_in=CAST((NEW.str_v::json->>'in')AS VARCHAR),
		 result_message=CAST((NEW.str_v::json->'out'->>'resultMessage')AS VARCHAR),
		 result_code=CAST((NEW.str_v::json->'out'->>'resultCode')AS VARCHAR),
		 result_data=CAST((NEW.str_v::json->'out'->>'resultData')AS VARCHAR),
		 response_data=CAST((NEW.str_v::json->'out'->>'responseData')AS VARCHAR),
		 randnum_key=CAST((NEW.str_v::json->'out'->>'RandNumKey')AS VARCHAR),
		 puid_key=CAST((NEW.str_v::json->'out'->>'PuidKey')AS VARCHAR),
		 cmd_key=CAST((NEW.str_v::json->'out'->>'CMDKey')AS VARCHAR),
		 control_key=CAST((NEW.str_v::json->'out'->>'ControlKey')AS VARCHAR)
		WHERE
			gas_ts=NEW.ts;
	END IF;
	*/
	--RETURN NEW;
 
	END IF;
	
	
	RETURN NEW;
	
	END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100