CREATE OR REPLACE FUNCTION "public"."func_attribute"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
	
	DECLARE 
		str VARCHAR:=NEW.str_v;
		imei VARCHAR:=NEW.str_v::json->>'IMEI' ;
		swVersion VARCHAR := NEW.str_v::json->>'当前软件版本号';
	BEGIN
	
	--entity_id过滤

	IF NEW.entity_id in ('1e98126fbd4d300a2f11feb32d3072b','1e985a834b61960a2f11feb32d3072b','1e985c80b4ba110a2f11feb32d3072b') THEN 
		IF	"length"(swVersion) >= 4 THEN
			swVersion = substring(swVersion, 3,2);
			swVersion = ltrim(swVersion, '0');
			--INSERT INTO "public"."temp"(i,str)VALUES(1,swVersion);
		END IF;
	-- Routine body goes here...
	IF NEW.attribute_type='CLIENT_SCOPE' AND LENGTH(NEW.attribute_key)=12 THEN
	IF (TG_OP='INSERT' AND NOT EXISTS(SELECT * FROM forweb.gas_attribute WHERE deviceidkey = NEW."attribute_key")) THEN
	   INSERT INTO forweb.gas_attribute(deviceidkey,commnokey,swrlsekey,readintervalkey,installtime,devicetype,fornumber,kind,puidkey) 
		 VALUES(NEW.attribute_key,
		 NEW.str_v::json->>'IMEI', 
		 swVersion,
		 NEW.str_v::json->>'采集周期',
		 NEW.str_v::json->>'首次采集时间',
		 "substring"(NEW.attribute_key, 1, 2),
		 "substring"(NEW.attribute_key, 3),
		 NEW.str_v::json->>'Kind',
		 NEW.str_v::json->>'PuidKey');
	ELSEIF(TG_OP='UPDATE') THEN
		IF EXISTS (SELECT * FROM forweb.gas_attribute WHERE deviceidkey=NEW."attribute_key") THEN
			UPDATE forweb.gas_attribute 
			SET commnokey=NEW.str_v::json->>'IMEI',
			swrlsekey=swVersion, 
			readintervalkey=NEW.str_v::json->>'采集周期' ,
			installtime=NEW.str_v::json->>'首次采集时间',
			devicetype="substring"(NEW.attribute_key, 1, 2),
			fornumber="substring"(NEW.attribute_key, 3),
			kind=NEW.str_v::json->>'Kind',
			puidkey=NEW.str_v::json->>'PuidKey'
			WHERE NEW."attribute_key"=deviceidkey;
		ELSE
			INSERT INTO forweb.gas_attribute(deviceidkey,commnokey,swrlsekey,readintervalkey,installtime,devicetype,fornumber,kind,puidkey) 
				VALUES(NEW.attribute_key,
				NEW.str_v::json->>'IMEI',
				swVersion,
				NEW.str_v::json->>'采集周期',
				NEW.str_v::json->>'首次采集时间',
				"substring"(NEW.attribute_key, 1, 2),
				"substring"(NEW.attribute_key, 3),
				NEW.str_v::json->>'Kind',
				NEW.str_v::json->>'PuidKey');
		END IF;	
	ELSE
		RETURN NEW;
END IF;
ELSE
	RETURN NEW;
END IF;
END IF;
	RETURN NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100