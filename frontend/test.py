from flask import Flask
 
app = Flask(__name__)

@app.route('/upload',methods = ['POST', 'GET'])
def submit_ass():
    print(request)
    return "OK"

if __name__ == '__main__':
   app.run(debug = True)