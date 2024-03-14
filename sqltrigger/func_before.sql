CREATE OR REPLACE FUNCTION "public"."func_before"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
	
	DECLARE 
		sum1 int;
		str_id INTEGER;
		str_ts VARCHAR:=NEW.ts;
		newCommandCode VARCHAR;
		str VARCHAR;
		json_t jsonb;
		
		-- 事件与警告
		i int4 := 0;
		count int4 := 0;
		ts_partation int8;
		
		-- 事件
		eventidkey VARCHAR;
		eventtimekey VARCHAR;
		eventcontentkey VARCHAR;
		
		-- 告警
		warningidkey VARCHAR;
		warningtimekey VARCHAR;
		warningcontentkey VARCHAR;
	BEGIN

	IF NEW.key is not NULL THEN
		SELECT ts_kv_dictionary.key into str from ts_kv,ts_kv_dictionary where ts_kv_dictionary.key_id = NEW.key;
	ELSE
		RETURN NEW;
	END IF;
	
	-- Routine body goes here...
  IF NEW.entity_id='fbd4d300-8126-11e9-a2f1-1feb32d3072b' THEN      --向消息表中插入数据，暂时只差入设备id和ts
	--INSERT INTO forweb.gas_message (deviceidkey,ts) VALUES("031511111122","1568687340000");
		INSERT INTO forweb.gas_message(deviceidkey,ts,ts_partation) 
		SELECT SUBSTRING(str,1,12), NEW.ts ,cast(NEW.ts as int8)
		WHERE NOT EXISTS (
    SELECT * FROM forweb.gas_message WHERE deviceidkey = SUBSTRING(str,1,12) AND
		ts=str_ts );
			
	ELSEIF NEW.entity_id='34b61960-85a8-11e9-a2f1-1feb32d3072b' THEN  --向告警表中插入数据，暂时只差入设备id和ts
	
		count = cast((NEW.json_v->>'count') as int4);
		
		WHILE i < count LOOP
			json_t = NEW.json_v->'Sub'->i;
			ts_partation = json_t->'ts';
			ts_partation = ts_partation + "floor"(random()*500);
			warningidkey = SUBSTRING(str,1,12) || '-告警代码';
			warningtimekey = SUBSTRING(str,1,12) || '-告警时间';
			warningcontentkey = SUBSTRING(str,1,12) || '-告警-累积量';
			INSERT INTO forweb.gas_warning(deviceidkey,ts,warningidkey,warningtimekey,warningcontentkey,ts_partation) 
			values(SUBSTRING(str,1,12),ts_partation,
			json_t->'values'->>warningidkey,json_t->'values'->>warningtimekey,
			json_t->'values'->>warningcontentkey,cast(ts_partation as int8));
			i = i + 1;	
		END LOOP;
	
	
		/*INSERT INTO forweb.gas_warning(deviceidkey,ts,ts_partation) 
		SELECT SUBSTRING(str,1,12), NEW.ts , cast(NEW.ts as int8)
		WHERE NOT EXISTS (
    SELECT * FROM forweb.gas_warning WHERE deviceidkey = SUBSTRING(str,1,12) AND
		ts=str_ts );
		*/
		
	ELSEIF NEW.entity_id='0b4ba110-85c8-11e9-a2f1-1feb32d3072b' THEN  --向事件表中插入数据，暂时只差入设备id和ts
		
		count = cast((NEW.json_v->>'count') as int4);
		
		WHILE i < count LOOP
			json_t = NEW.json_v->'Sub'->i;
			
			ts_partation = json_t->'ts';
			ts_partation = ts_partation + "floor"(random()*500);
			eventidkey = SUBSTRING(str,1,12) || '-事件代码';
			eventtimekey = SUBSTRING(str,1,12) || '-事件时间';
			eventcontentkey = SUBSTRING(str,1,12) || '-事件-累积量';
	
			INSERT INTO forweb.gas_event(deviceidkey,ts,eventidkey,eventtimekey,eventcontentkey,ts_partation) 
			values(SUBSTRING(str,1,12),ts_partation,
			json_t->'values'->>eventidkey,json_t->'values'->>eventtimekey,
			json_t->'values'->>eventcontentkey,cast(ts_partation as int8));
			i = i + 1;	
		END LOOP;
		
		/*INSERT INTO forweb.gas_event(deviceidkey,ts,ts_partation) 
		SELECT SUBSTRING(str,1,12), NEW.ts,cast(NEW.ts as int8) 
		WHERE NOT EXISTS (
    SELECT * FROM forweb.gas_event WHERE deviceidkey = SUBSTRING(str,1,12) AND
		ts=str_ts );
		*/
		
  ELSEIF NEW.entity_id='efe583c0-81c4-11e9-a2f1-1feb32d3072b' THEN --向解析中间表中插入数据，暂时只差入设备ts和扫描标志位	
	  --INSERT INTO forweb.gas_data_analy_sys_log1(gas_ts,sign,gas_in) VALUES(NEW.ts,cast((0)as int4),CAST((NEW.str_v::json->>'in')AS VARCHAR));
		INSERT into forweb.gas_data_analy_sys_log1(gas_ts,gas_key,result_message,result_code,
		result_data,response_data,randnum_key,puid_key,cmd_key,control_key,sign,gas_in)
		VALUES(
		NEW.ts,
		NEW.key,
		CAST((NEW.str_v::json->'out'->>'resultMessage')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'resultCode')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'resultData')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'responseData')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'RandNumKey')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'PuidKey')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'CMDKey')AS VARCHAR),
		CAST((NEW.str_v::json->'out'->>'ControlKey')AS VARCHAR),
		CAST((0) AS int4),
		CAST((NEW.str_v::json->>'in')AS VARCHAR)
		);
		
	ELSEIF NEW.entity_id='dd37b1a0-5c29-11e9-a2f1-1feb32d3072b' THEN   --向命令应答表中插入数据		
	
		
		json_t = NEW.str_v::json;
		--insert into "public"."temp"(str,str_v) values(str,NEW.str_v);
		--json_t = to_json(NEW.str_v);
		
		INSERT INTO forweb.gas_response(deviceidkey,ts,response,commandcode) SELECT str, NEW.ts ,NEW.str_v,json_t->>'CommandCode'
		WHERE NOT EXISTS (
    SELECT * FROM forweb.gas_response WHERE deviceidkey = str AND ts=str_ts AND 
		response=NEW.str_v AND commandcode=json_t->>'CommandCode');	
		
		
		IF json_t->>'CommandCode' in ('F0') THEN
			
			UPDATE forweb.gas_attribute SET commnokey = json_t->>'IEMI',simnumber = json_t->>'ICCID' where deviceidkey = str;
		END IF;
		
		
	ELSEIF NEW.entity_id='4110dec0-5c04-11e9-a2f1-1feb32d3072b' THEN  --向命令表插入数据
		IF (NEW.str_v::json->>'OPTypeKey' = '01') THEN
			newCommandCode = '81';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '02') THEN
			newCommandCode = '83';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '03') THEN
			newCommandCode = '87';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '04') THEN
			newCommandCode = '86';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '05') THEN
			newCommandCode = '8E';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '06') THEN
			newCommandCode = '85';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '07') THEN
			newCommandCode = '84';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '08') THEN
			newCommandCode = '8D';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '09') THEN
			newCommandCode = null;
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '10') THEN
			IF(SUBSTRING(str,0,3) = '00') THEN
				newCommandCode = '0F';
			ELSEIF(SUBSTRING(str,0,3) = '03') THEN
				newCommandCode = '5F';
			END IF;
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '11') THEN
			newCommandCode = '88';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '12') THEN
			newCommandCode = 'A1';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '13') THEN
			newCommandCode = 'A2';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '14') THEN
			IF(SUBSTRING(str,0,3) = '00') THEN
				newCommandCode = '0F';
			ELSEIF(SUBSTRING(str,0,3) = '03') THEN
				newCommandCode = '5F';
			END IF;
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '15')THEN
			newCommandCode = '82';
		ELSEIF(NEW.str_v::json->>'OPTypeKey' = '80')THEN
			newCommandCode = 'F0';
		END IF;
		INSERT INTO forweb.gas_command(deviceidkey,ts,commandcode,command,ts_partation) 
					 SELECT str,NEW.ts,	newCommandCode,NEW.str_v,cast(NEW.ts as int8) 
		WHERE NOT EXISTS(
		SELECT * FROM forweb.gas_command WHERE deviceidkey = str AND ts=str_ts AND command = NEW.str_v
		);

	END IF;
	RETURN NEW;
	EXCEPTION
	WHEN OTHERS THEN
	RETURN NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100