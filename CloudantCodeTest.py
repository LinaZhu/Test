import requests
import json

def create_database(username,password,dbname):
	result = requests.put("https://%s.cloudant.com/%s" % (username,dbname), auth=(username, password))
	print result.content
	res = json.loads(result.content)
	if "ok" in res:
		print "Create database done, db name: %s" % dbname
	else:
		print "ERROR found: %s" % res["reason"]
	
def create_document(username,password,dbname,docname):
	result = requests.put("https://%s.cloudant.com/%s/%s" % (username,dbname,docname), auth=(username, password),\
		headers={"content-type":"application/json"},\
		data=json.dumps({"foo":"bar"}))
	print result.content 
	res = json.loads(result.content)
	if "ok" in res:
		print "Create document done, document name: %s" % docname
	else:
		print "ERROR found: %s " % res["reason"]

def get_document(username,password,dbname,docname):
	result = requests.get("https://%s.cloudant.com/%s/%s" % (username,dbname,docname), \
		auth=(username, password))

	print result.content
	res = json.loads(result.content)
	if "error" in res:
		print "ERROR found: %s" % res["reason"]
	else:
		print "Get doc done, doc id: %s, revision: %s can be found" % (res["_id"],res["_rev"])

if __name__ == "__main__":
	username = "lina	"
	password = "123456789"
	dbname = "mytestdb_001"
	docname = "mydoc_001"
	create_database(username, password,dbname)
	create_document(username,password,dbname,docname)
	get_document(username,password,dbname,docname)