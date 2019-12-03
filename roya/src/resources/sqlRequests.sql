salaryRequest:= select '000'||  rpad(trim(to_char(prop_value)),8,' ')  ||  TRIM(TO_CHAR(NULL,'YYYYMMDD')) || rpad(trim(to_char('X')),35,' ')   as salary from HRS_SALARY_PROPERTIES 
                     where prop_name  = 'Agreement Number'  
                    union all select replace(112 || '000000' || rpad(trim(to_char(hrs_master_file.empno)),16,' ') || rpad(trim(to_char(hrs_master_file.natno)),12,' ') 
                    || rpad(trim(to_char(pay_banks.bank_swift)),11,' ')   ||  rpad(trim(to_char(hrs_master_file.accno)),34,' ') || 
                    trim(to_char((( nvl(SAL.SAL_DESERVED,0) + nvl(SAL.SAL_TRANS,0) +nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) +nvl(SAL.SAL_DARAR,0) + 
                    nvl(SAL.SAL_OTHER_IN,0)+nvl(SAL.life,0)  )-(nvl(SAL.SAL_RETIRE,0)+nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + 
                    nvl(SAL.SAL_BANK_4,0)+nvl(SAL.SAL_BANK_5,0)+nvl(SAL.SAL_OTHER_out,0)+nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0))),'000000000000.99')) || 'SAR' || 
                    rpad(trim(to_char('X')),35,' ') || rpad(trim(to_char('X')),20,' ')   || rpad(trim(to_char('X')),9,' ') || 
                    rpad(substr(FUN_GET_INF001_AR(hrs_master_file.empno),1,35),35,' ') || rpad(trim(to_char('X')),140 ,' ') , chr(10),'') AS SALARY 
                    FROM hrs_salary sal, hrs_master_file, pay_banks,hrs_emp_historical hist WHERE (    (hrs_master_file.empno = sal.empno) 
                    AND (pay_banks.ID = hrs_master_file.bnkcod)) and  hrs_master_file.EMPNO = sal.empno and hrs_master_file.EMPSTS=1 and hist.empno = sal.empno 
                    and hist.flg = 1 and sal_month = :month and sal_year = :year union all select '999'|| sum(trim(to_char (((( nvl(SAL.SAL_DESERVED,0) + nvl(SAL.SAL_TRANS,0) 
                    + nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) + nvl(SAL.SAL_DARAR,0) + nvl(SAL.SAL_OTHER_IN,0)+nvl(SAL.life,0) )-(nvl(SAL.SAL_RETIRE,0)+
                    nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + nvl(SAL.SAL_BANK_4,0)+nvl(SAL.SAL_BANK_5,0)+
                    nvl(SAL.SAL_OTHER_out,0)+nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0)))),'000000000000000.99'))) || lpad(trim(to_char(count((( nvl(SAL.SAL_DESERVED,0) 
                    + nvl(SAL.SAL_TRANS,0) + nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) + nvl(SAL.SAL_DARAR,0) + nvl(SAL.SAL_OTHER_IN,0) )-
                    (nvl(SAL.SAL_RETIRE,0)+nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + nvl(SAL.SAL_BANK_4,0)+
                    nvl(SAL.SAL_BANK_5,0)+nvl(SAL.SAL_OTHER_out,0)+nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0)))))),6,'0') as salary 
                    FROM hrs_salary sal , hrs_master_file HMF,hrs_emp_historical hist where sal_month = :month and sal_year =  :year
                     and  HMF.EMPNO = sal.empno  and hist.empno = sal.empno and hist.flg = 1;

overTimeRequest := select '000'||  rpad(trim(to_char(prop_value)),8,' ')  ||  TRIM(TO_CHAR(NULL,'YYYYMMDD')) || rpad(trim(to_char('X')),35,' ')   as salary from HRS_SALARY_PROPERTIES 
                     where prop_name  = 'Agreement Number'  
                    union all select replace(112 || '000000' || rpad(trim(to_char(hrs_master_file.empno)),16,' ') || rpad(trim(to_char(hrs_master_file.natno)),12,' ') 
                    || rpad(trim(to_char(pay_banks.bank_swift)),11,' ')   ||  rpad(trim(to_char(hrs_master_file.accno)),34,' ') || 
                    trim(to_char( nvl(ototal,0),'000000000000.99')) || 'SAR' || 
                    rpad(trim(to_char('X')),35,' ') || rpad(trim(to_char('X')),20,' ')   || rpad(trim(to_char('X')),9,' ') || 
                    rpad(substr(FUN_GET_INF001_AR(hrs_master_file.empno),1,35),35,' ') || rpad(trim(to_char('X')),140 ,' ') , chr(10),'') AS SALARY 
                    FROM hrs_emp_ovrtm sal, hrs_master_file, pay_banks,hrs_emp_historical hist WHERE (    (hrs_master_file.empno = sal.empno) 
                    AND (pay_banks.ID = hrs_master_file.bnkcod)) and  hrs_master_file.EMPNO = sal.empno and hrs_master_file.EMPSTS=1 and hist.empno = sal.empno 
                    and hist.flg = 1 and month = :month and year = :year 
                    and sal.PCAT in (1,2,4,9)
                    union all select '999'|| trim(to_char (sum(nvl(ototal,0)),'000000000000000.99')) || lpad(trim(to_char(count(nvl(ototal,0)))),6,'0') as salary 
                    FROM hrs_emp_ovrtm sal , hrs_master_file HMF,hrs_emp_historical hist where month = :month and year =  :year
                    and sal.PCAT in (1,2,4,9)
                     and  HMF.EMPNO = sal.empno  and hist.empno = sal.empno and hist.flg = 1;
dept_records_received:= select  id,donner, donner_dept_id,donner_dept_name,sign_date,
arc_id,income_no,subject,attach_nb,receiver_dept_id,receiver_dep_name  
from  
dept_arc_records;

dept_records_not_received:= select
 0 donner, 0 DONNER_DEPT_ID ,' ' DONNER_DEPT_NAME, to_date(sysdate,'dd/mm/yyyy') SIGN_DATE, 
arc.id arc_id,to_number(arc.income_no)income_no, rec_title subject, attach attach_nb, toUs.dept_id receiver_dept_id, dept.dept_name receiver_dep_name 
 from  arc_records arc,wrk_application wrk, arc_users us,arc_users toUs,wrk_dept  dept,
           ( 
           select rrec_id,            
            COUNT(rrec_id)attach 
           FROM 
              arc_rec_att 
            GROUP BY
            rrec_id
            )atchs 
where   
    ARC.ID        = wrk.app_id    
and arc.id        = atchs.rrec_id  (+) 
and wrk.from_id   = us.user_id  
and us.dept_id    = :dept_id 
and is_visible    = 1   
and toUs.user_id  = to_id 
and toUs.dept_id  = dept.id 
and to_id not in (264,86) 

MINUS 

 select  
  donner, DONNER_DEPT_ID , DONNER_DEPT_NAME, SIGN_DATE, 
  arc_id            ,
  income_no         ,
  subject           ,
  attach_nb         ,
  receiver_dept_id  ,
  receiver_dep_name   
from 
dept_arc_records   
where   
 donner_dept_id = :dept_id   ;

user_hrs_absents:= select user_id as empno,sum(days)as days,sum(hours)as hours,sum(mins)as minutes 
from  HRS_EMP_ABSNT abs, arc_users us 
 where    
    abs.empno = us.empno and
    ABSTYPE   = 204  and 
    absyear   = ? 

group by us.user_id ;

salaryRequestrsd:= SELECT (
 rpad(trim(to_char(h_record_type)),3,' ')
||rpad(trim(to_char(agreement_code)),8,' ')
||rpad(trim(TO_CHAR(to_date('20181227','yyyymmdd'),'yyyymmdd')),8,' ')
||rpad(trim(to_char(trans_label)),15,' ')
||rpad(trim(TO_CHAR(to_date('20181227','yyyymmdd'),'yymmdd')),20,' ')
||rpad(trim(to_char(agence_name)),150,' ')
||rpad(' ',150,' ') --dept name
||rpad(trim(to_char(account_nbr)),34,' ')
||rpad(' ',15,' ') --order_nbr
||lpad(trim(to_char(sector_nbr)),2,'0')
||lpad(trim(to_char(class_nbr)),3,'0')
||lpad(trim(to_char(branch_nbr)),3,'0')
||lpad(trim(to_char(division_nbr)),3,'0')
||lpad(trim(to_char(sub_div_nbr)),3,'0')
||rpad(' ',483,' ')

)as salary
FROM 
salary_file_properties 
WHERE 
id = 1
UNION ALL

select   (		rpad(trim(to_char(d_record_type)),3,' ') ||
 					lpad(trim(to_char(rownum)),6,'0') ||
 					rpad(trim(to_char(rownum)),16,' ') || rpad(trim(to_char(hrs_master_file.EMPNO)),12,' ') 
                    || rpad(trim(to_char(pay_banks.bank_swift)),11,'X')   ||  rpad(trim(to_char(hrs_master_file.accno)),34,' ') || 
                    trim(to_char((( nvl(SAL.SAL_DESERVED,0) + nvl(SAL.SAL_TRANS,0) +nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) +nvl(SAL.SAL_DARAR,0) + 
                    nvl(SAL.SAL_OTHER_IN,0)+nvl(SAL.life,0)  )-
                    
                    (nvl(SAL.SAL_RETIRE,0)+nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + 
                    nvl(SAL.SAL_BANK_4,0)+nvl(SAL.SAL_BANK_5,0)+nvl(SAL.SAL_OTHER_out,0)
                      +nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0))),'000000000000.99')) || 'SAR' || 
                    rpad(trim(to_char(' ')),35,' ') || rpad(trim(to_char(' ')),20,' ')   ||
                    rpad(trim(to_char(' ')),9,' ') || 
                    rpad(substr(FUN_GET_INF001_AR(hrs_master_file.empno),1,35),35,' ')||
                    rpad(trim(to_char(' ')),200,' ') || 
                    rpad(trim(to_char('راتب شهري')),140 ,' ') ||
                    rpad(trim(to_char(hrs_master_file.natno)),18,' ')|| ---الهوية الوطنية
                    rpad(trim(to_char(hrs_master_file.natno)),200,' ')||
                    rpad(trim(to_char(nvl(SAL.SAL_DESERVED,0))),'000000000000.99') ||--  الراتب الاساسي من السلم الوظيفي
                    rpad(trim(to_char( nvl(SAL.SAL_TRANS,0) +nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) +nvl(SAL.SAL_DARAR,0) + nvl(SAL.SAL_OTHER_IN,0)+nvl(SAL.life,0))),'000000000000.99') ||--البدلات
                     rpad(trim(to_char( (nvl(SAL.SAL_RETIRE,0)+nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + 
                    nvl(SAL.SAL_BANK_4,0)+nvl(SAL.SAL_BANK_5,0)+nvl(SAL.SAL_OTHER_out,0) +nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0)))),'000000000000.99') ||--الخصومات
                    rpad(trim(TO_CHAR(to_date('20181227','yyyymmdd'),'yyyymm')),6,' ') ||
                    rpad(trim(to_char(SYS037.NAME)),50,' ') ||--اسم السلم الوظيفي )(بند اجورز موظفين
                    rpad(trim(to_char('N')),1,'N') ||
                    rpad(trim(to_char(' ')),41,' ')
                   
                    ) AS SALARY                    
FROM
	hrs_salary sal, hrs_master_file, pay_banks,hrs_emp_historical hist ,salary_file_properties sal_prop, SYS037
WHERE (    (hrs_master_file.empno = sal.empno) 
AND (pay_banks.ID = hrs_master_file.bnkcod)) and  hrs_master_file.EMPNO = sal.empno and hrs_master_file.EMPSTS=1 and hist.empno = sal.empno 
 and hist.flg = 1 and sal.SAL_MONTH = :month and sal.SAL_YEAR= :year 
 AND sal_prop.id = 1
 AND sys037.ID = sal.CATCOD AND sys037.ID =1 AND hrs_master_file.CATCOD= 1
  
 UNION ALL
 
SELECT (rpad(trim(to_char(f_record_type)),3,' ')|| trim(to_char(totsalary.salary,'000000000000000.99'))||lpad(nbr_instructions,6,'0')||lpad(' ',873,' '))salary
FROM 
salary_file_properties ,
(select 
    sum(trim(to_char (((( nvl(SAL.SAL_DESERVED,0) + nvl(SAL.SAL_TRANS,0) 
                    + nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) + nvl(SAL.SAL_DARAR,0) + nvl(SAL.SAL_OTHER_IN,0)+nvl(SAL.life,0) )-(nvl(SAL.SAL_RETIRE,0)+
                    nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + nvl(SAL.SAL_BANK_4,0)+nvl(SAL.SAL_BANK_5,0)+
                    nvl(SAL.SAL_OTHER_out,0)+nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0)))),'000000000000000.99'))) || lpad(trim(to_char(count((( nvl(SAL.SAL_DESERVED,0) 
                    + nvl(SAL.SAL_TRANS,0) + nvl(SAL.SAL_NATURAL,0) + nvl(SAL.SAL_NATURE_BASIC,0) + nvl(SAL.SAL_DARAR,0) + nvl(SAL.SAL_OTHER_IN,0) )-
                    (nvl(SAL.SAL_RETIRE,0)+nvl(SAL.SAL_INSURANCE,0) +nvl(SAL.SAL_BANK_1,0) +nvl(SAL.SAL_BANK_2,0) + nvl(SAL.SAL_BANK_3,0) + nvl(SAL.SAL_BANK_4,0)+
                    nvl(SAL.SAL_BANK_5,0)+nvl(SAL.SAL_OTHER_out,0)+nvl(SAL_JAZA_days,0)+nvl(SAL.SAL_SANED,0)))))),6,'0') as salary,count(*)nbr_instructions 
FROM hrs_salary sal , hrs_master_file HMF,hrs_emp_historical hist 
where 
	sal.sal_month = :month and sal.SAL_YEAR = :year 
and  HMF.EMPNO = sal.empno  and hist.empno = sal.empno and hist.flg = 1

)totsalary
WHERE id =1; 

DeptTrainRequest:= select '000'||  rpad(trim(to_char(prop_value)),8,' ')  ||  TRIM(TO_CHAR(NULL,'YYYYMMDD')) || rpad(trim(to_char('X')),35,' ')   as salary from HRS_SALARY_PROPERTIES 
	                    where prop_name  = 'Agreement Number'  
	                    union ALL select replace(112 || '000000' || rpad(trim(to_char(hrs_master_file.empno)),16,' ') || rpad(trim(to_char(hrs_master_file.natno)),12,' ') 
	                    || rpad(trim(to_char(pay_banks.bank_swift)),11,' ')   ||  rpad(trim(to_char(hrs_master_file.accno)),34,' ') || 
	                    trim(to_char(((nvl(DEPUTATION_TRAINING.TRANSPORT_ALLOWANCE,0)+nvl(DEPUTATION_TRAINING.TRAINING_ALLOWANCE,0))),'000000000000.99')) || 'SAR' || 
	                    rpad(trim(to_char('X')),35,' ') || rpad(trim(to_char('X')),20,' ')   || rpad(trim(to_char('X')),9,' ') || 
	                    rpad(substr(FUN_GET_INF001_AR(hrs_master_file.empno),1,35),35,' ') || rpad(trim(to_char('X')),140 ,' ') , chr(10),'') AS SALARY 
	                    FROM DEPUTATION_TRAINING, hrs_master_file, pay_banks WHERE (    (hrs_master_file.empno = DEPUTATION_TRAINING.MASTER_EMPNO) 
	                    AND (pay_banks.ID = hrs_master_file.bnkcod)) and  hrs_master_file.EMPNO = DEPUTATION_TRAINING.MASTER_EMPNO and hrs_master_file.EMPSTS=1 AND op_year =:year AND op_month =:month
	                    union all select '999'|| sum(trim(to_char (((nvl(DEPUTATION_TRAINING.TRAINING_ALLOWANCE,0)+nvl(DEPUTATION_TRAINING.TRANSPORT_ALLOWANCE,0))),'000000000000000.99'))) 
	                    FROM  DEPUTATION_TRAINING , hrs_master_file HMF where HMF.EMPNO = DEPUTATION_TRAINING.MASTER_EMPNO AND op_year =:year AND op_month =:month;
RewardRequest:= select '000'||  rpad(trim(to_char(prop_value)),8,' ')  ||  TRIM(TO_CHAR(NULL,'YYYYMMDD')) || rpad(trim(to_char('X')),35,' ')   as salary from HRS_SALARY_PROPERTIES 
	                    where prop_name  = 'Agreement Number'  
	                    union ALL select replace(112 || '000000' || rpad(trim(to_char(hrs_master_file.empno)),16,' ') || rpad(trim(to_char(hrs_master_file.natno)),12,' ') 
	                    || rpad(trim(to_char(pay_banks.bank_swift)),11,' ')   ||  rpad(trim(to_char(hrs_master_file.accno)),34,' ') || 
	                    trim(to_char(((nvl(reward.amount,0))),'000000000000.99')) || 'SAR' || 
	                    rpad(trim(to_char('X')),35,' ') || rpad(trim(to_char('X')),20,' ')   || rpad(trim(to_char('X')),9,' ') || 
	                    rpad(substr(FUN_GET_INF001_AR(hrs_master_file.empno),1,35),35,' ') || rpad(trim(to_char('X')),140 ,' ') , chr(10),'') AS SALARY 
	                    FROM reward, hrs_master_file, pay_banks WHERE (    (hrs_master_file.empno = reward.MASTER_EMPNO) 
	                    AND (pay_banks.ID = hrs_master_file.bnkcod)) and  hrs_master_file.EMPNO = reward.MASTER_EMPNO and hrs_master_file.EMPSTS=1 AND reward_year =:year AND reward_month =:month
	                    union all select '999'|| sum(trim(to_char (((nvl(reward.amount,0))),'000000000000000.99'))) 
	                    FROM  reward , hrs_master_file HMF where HMF.EMPNO = reward.MASTER_EMPNO AND reward_year =:year AND reward_month =:month ;
