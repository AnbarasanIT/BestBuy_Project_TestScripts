
Insert into ROLES (ID,NAME,DESCRIPTION) values (1,'ROLE_ADMIN','Administrative');
Insert into ROLES (ID,NAME,DESCRIPTION) values (2,'ROLE_USER','Individual Contributor');

--sqls for the USERS
Insert into USERS (USER_ID,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,LOGIN_NAME,PASSWORD,UPDATED_BY,UPDATED_DATE,ROLE_ID) values (10201,1,'Liz.Wright@bestbuy.com','Liz','Wright','a036034',null,'BestBuy, MerchandisingUI (a1234567)',to_date('14-DEC-12','DD-MON-RR'),1);
Insert into USERS (USER_ID,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,LOGIN_NAME,PASSWORD,UPDATED_BY,UPDATED_DATE,ROLE_ID) values (10202,1,'Chanchal.Kumari@bestbuy.com','Chanchal','Kumari','a1003132',null,'BestBuy, MerchandisingUI (a1234567)',to_date('14-DEC-12','DD-MON-RR'),1);
Insert into USERS (USER_ID,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,LOGIN_NAME,PASSWORD,UPDATED_BY,UPDATED_DATE,ROLE_ID) values (10203,1,'Christy.Bennet@bestbuy.com','Christy','Bennett','a005271',null,'BestBuy, MerchandisingUI (a1234567)',to_date('14-DEC-12','DD-MON-RR'),1);
Insert into USERS (USER_ID,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,LOGIN_NAME,PASSWORD,UPDATED_BY,UPDATED_DATE,ROLE_ID) values (10204,1,'Ishan.Paisal@bestbuy.com','Ishan','Paisal','a1007927',null,'BestBuy, MerchandisingUI (a1234567)',to_date('14-DEC-12','DD-MON-RR'),1);
Insert into USERS (USER_ID,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,LOGIN_NAME,PASSWORD,UPDATED_BY,UPDATED_DATE,ROLE_ID) values (10205,1,'test.user@bestbuy.com','Test', 'User','a1010742',null,'BestBuy, MerchandisingUI (a1234567)',to_date('14-DEC-12','DD-MON-RR'),1);

--sqls for the SYNONYM_TYPES

Insert into SYNONYM_TYPES (ID,NAME) values (1133827231862,'Global_syn_list1');
Insert into SYNONYM_TYPES (ID,NAME) values (1147192016834,'Artist');
Insert into SYNONYM_TYPES (ID,NAME) values (1147192098290,'music');
Insert into SYNONYM_TYPES (ID,NAME) values (1147192120811,'song');


-- sqls for the STATUS
Insert into STATUS (STATUS_ID,STATUS_NAME) values (2,'Draft');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (3,'Approved');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (4,'Expired');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (6,'Rejected');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (7,'Deleted');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (8,'Published CF');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (9,'Not Valid');
Insert into STATUS (STATUS_ID,STATUS_NAME) values (10,'Published AF');

--sqls for the SEARCH_PROFILES
Insert into SEARCH_PROFILES (SEARCH_PROFILE_ID,COLLECTIONS,DEFAULT_PATH,LASTMODIFIER_ID,MODIFIED_DATE,PIPELINE_NAME,PROFILE_NAME,RANK_PROFILE_NAME,SEARCH_FIELD_NAME,TOP_CATEGORY_ID,SYNONYM_LIST_ID) values (1,'products','ncrootcategoryid','a1234567',to_date('07-MAR-11 12.00.00','DD-MON-RR HH.MI.SS'),'production','Global','default','content','rootcategoryid',1133827231862);


-- sqls for the banner Templates
Insert into BANNER_TEMPLATE_META (BANNER_TEMPLATE_ID,BANNER_TEMPLATE_FIELD_LIST,BANNER_TEMPLATE_HEADER_COUNT,BANNER_TEMPLATE_NAME,BANNER_TEMPLATE_SLOT_COUNT) values (1,'assetId',0,'HTML_ONLY',0);
Insert into BANNER_TEMPLATE_META (BANNER_TEMPLATE_ID,BANNER_TEMPLATE_FIELD_LIST,BANNER_TEMPLATE_HEADER_COUNT,BANNER_TEMPLATE_NAME,BANNER_TEMPLATE_SLOT_COUNT) values (2,'header',1,'1HEADER_3SKU',3);
Insert into BANNER_TEMPLATE_META (BANNER_TEMPLATE_ID,BANNER_TEMPLATE_FIELD_LIST,BANNER_TEMPLATE_HEADER_COUNT,BANNER_TEMPLATE_NAME,BANNER_TEMPLATE_SLOT_COUNT) values (3,'header',3,'3Header_3SKU',1);

commit;