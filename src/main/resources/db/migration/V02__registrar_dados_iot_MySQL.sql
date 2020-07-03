-- MySQL
-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



-- -----------------------------------------------------
-- Inserindo dados na Table `tccfacid`.`RAW_BODIES`
-- -----------------------------------------------------
INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa","344C390D3CE9F575",1,1,null,false,"QWRhaWxTaWx2YQ==",null,"UPLINK",null,null,"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/devices_au915-928/uplink?key=ttn-account-v2.mjBu1zaGjfPQHCsUTkCY3gW2s-eMNJg6tz3-4fsg4hw");

INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa",null,1,null,null,true,"AQIDBA==","replace","DOWNLINK","scheduled confirmed",null,"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/devices_au915-928/uplink?key=ttn-account-v2.mjBu1zaGjfPQHCsUTkCY3gW2s-eMNJg6tz3-4fsg4hw");

INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa",null,1,null,null,false,null,null,"DOWNLINK","confirmed",null,null);

INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa","344C390D3CE9F575",0,2,null,true,null,null,"UPLINK","confirmed ack",null,"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/devices_au915-928/uplink?key=ttn-account-v2.mjBu1zaGjfPQHCsUTkCY3gW2s-eMNJg6tz3-4fsg4hw");

INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa",null,1,null,null,false,"AQIDBA==","replace","DOWNLINK","scheduled",null,"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/devices_au915-928/uplink?key=ttn-account-v2.mjBu1zaGjfPQHCsUTkCY3gW2s-eMNJg6tz3-4fsg4hw");

INSERT INTO raw_bodies (app_id,dev_id,hardware_serial,port,counter,is_retry,confirmed,payload_raw,schedule,filter,ack,http_status,downlink_url) values ("devices_au915-928","mote_09_d1mini_otaa","344C390D3CE9F575",1,3,null,false,"QWRhaWxTaWx2YQ==",null,"UPLINK",null,null,"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/devices_au915-928/uplink?key=ttn-account-v2.mjBu1zaGjfPQHCsUTkCY3gW2s-eMNJg6tz3-4fsg4hw");



-- -----------------------------------------------------
-- Inserindo dados na Table `tccfacid`.`PAYLOAD_FIELDS`
-- -----------------------------------------------------
INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",1);

--  Não necessita: ID 2 é um Downlink.
-- INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",2);

--  Não necessita: ID 3 é um Downlink.
-- INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",3);

--  Não necessita: ID 4 é um uplink, porém não necessita de nenhum relacionamento.
-- INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",4);

--  Não necessita: ID 5 é um Downlink.
-- INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",5);

INSERT INTO payload_fields (text,raw_bodies_id) values ("AdailSilva",6);



-- -----------------------------------------------------
-- Inserindo dados na Table `tccfacid`.`METADATA`
-- -----------------------------------------------------
INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,1);

--  Não necessita: ID 2 é um Downlink.
-- INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,2);

--  Não necessita: ID 3 é um Downlink.
-- INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,3);

--  Não necessita: ID 4 é um uplink, porém não necessita de nenhum relacionamento.
-- INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,4);

--  Não necessita: ID 5 é um uplink, porém não necessita de nenhum relacionamento.
-- INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,5);

INSERT INTO metadata (time,frequency,modulation,data_rate,bit_rate,coding_rate,latitude,longitude,altitude,raw_bodies_id) values ("2019-10-10T18:22:33.252195669Z",916.8,"LORA","SF7BW125",null,"4/5",-5.00001,-42.11111,100,6);



-- -----------------------------------------------------
-- Inserindo dados na Table `tccfacid`.`GATEWAYS`
-- -----------------------------------------------------
INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,1);

INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,2);

INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,1);

INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,2);

INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,1);

INSERT INTO gateways (gtw_id,timestamp,time,channel,rssi,snr,rf_chain,latitude,longitude,altitude,metadata_id) values ("eui-a840411b7dcc4150","2083455420","2019-10-10T18:22:33.144702Z",0,-60,7.8,0,-5.04761,-42.78149,25,2);
