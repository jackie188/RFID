//�Ƿ�Ϊ��У��
function TrimAll(str){
		var result;
		result = str.replace(/(^\s+)|(\s+$)/g,"");
		result = result.replace(/\s/g,"");
		result = result.replace(" ","");
		result = result.replace(/\s+/g,"");
		//alert(result);
		return result;
}

function isEmpty(s) {
	var lll=trim(s);
	if( lll == null || lll.length == 0 )
		return true;
	else
		return false;
}

//ɾ���ַ�����ߵĿո�
function ltrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=0;
		while(str.charAt(idx).search(/\s/)==0)
			idx++;
		return(str.substr(idx));
	}
}

//ɾ���ַ����ұߵĿո�
function rtrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=str.length-1;
		while(str.charAt(idx).search(/\s/)==0)
			idx--;
		return(str.substring(0,idx+1));
	}
}

//ɾ���ַ����������ߵĿո�
function trim(str) { 
	return(rtrim(ltrim(str)));
}

/*������Ƚ�*/
function compareDate(date1, date2) {
	if (trim(date1) == trim(date2))  	
		return 0;
	if (trim(date1) > trim(date2))  	
		return 1;
	if (trim(date1) < trim(date2))  	
		return -1;
}

//У���Ƿ���Email
function isEmail(eml) {
	if(trim(eml)!='') {
	  var re=new RegExp("@[\\w]+(\\.[\\w]+)+$");
	  return(re.test(eml));
	}
	else
	  return(true);
}

//�Ƿ��ǵ绰��
function isTel(tel) {
	var charcode;
	for (var i=0; i<tel.length; i++)	
	{
		charcode = tel.charCodeAt(i);
		if (charcode < 48 && charcode != 45 || charcode > 57)	
			return false;
	}
	return true;
}

//У���Ƿ���ʵ��
function isnumber(num) {
	var re=new RegExp("^-?[\\d]*\\.?[\\d]*$");
	if(re.test(num))
		return(!isNaN(parseFloat(num)));
	else
		return(false);
}

//У���Ƿ�������
function isinteger(num)	{
	var re=new RegExp("^-?[\\d]*$");
	if(re.test(num))
		return(!isNaN(parseInt(num)));
	else
		return(false);
}