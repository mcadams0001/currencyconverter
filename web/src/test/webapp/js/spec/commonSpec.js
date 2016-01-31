describe('Class Common', function(){
   it('function isValidDouble', function(){
      expect(common.isValidDouble("2.0")).toBeTruthy();
      expect(common.isValidDouble("200")).toBeTruthy();
      expect(common.isValidDouble("200.123")).toBeTruthy();
      expect(common.isValidDouble("200.123a")).toBeFalsy();
   });
   it('function isValidDate', function(){
      expect(common.isValidDate("20-Apr-2000")).toBeTruthy();
      expect(common.isValidDate("20-Dec-2000")).toBeTruthy();
      expect(common.isValidDate("1-Dec-2000")).toBeTruthy();
      expect(common.isValidDate("1a-Dec-2000")).toBeFalsy();
   });

});