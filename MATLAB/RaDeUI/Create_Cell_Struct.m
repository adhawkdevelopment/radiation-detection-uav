%% Cell Structure for QR Location Data

% This script is used to develop the look up table for user interface

% Output: 2 x N Cell 
% Each column N corresponds to location number, which is obtaine from QR
% reader.  Each QR code corresponds to a single number 1:N. 
% First row of output corresponds to physical coordinates of location (1x3) type int
% Second row of output corresponds to description of location (1x1) type string



% Physical location coordinates
loc1 = [5,5];
loc2 = [0,5];
loc3 = [-5,5];
loc4 = [-5,0];
loc5 = [5,-5];
loc6 = [0,-5];
loc7 = [-5,-5];
loc8 = [5,0];
loc9 = [3,3];
loc10 = [-3,-3];

% Descriptions
desc1 = 'North East Corner';
desc2 = 'Northern Pipe';
desc3 = 'North West Corner';
desc4 = 'Western Pipe';
desc5 = 'South East Corner';
desc6 = 'Southern Pipe';
desc7 = 'South West Corner';
desc8 = 'Eastern Pipe';
desc9 = 'North East Corner of Reactor';
desc10 = 'South West Corner of Reactor';



% GM counts per second (row 3)
cps1 = 0;
cps2 = 0;
cps3 = 0;
cps4 = 0;
cps5 = 0;
cps6 = 0;
cps7 = 0;
cps8 = 0;
cps9 = 0;
cps10 = 0;


% Time and date (row 4)
t_d_1 = '';
t_d_2 = '';
t_d_3 = '';
t_d_4 = '';
t_d_5 = '';
t_d_6 = '';
t_d_7 = '';
t_d_8 = '';
t_d_9 = '';
t_d_10 = '';


% Creates cell structure
qr_lookup = cell(4,10);
qr_lookup(1,:) = {loc1,loc2,loc3,loc4,loc5,loc6,loc7,loc8,loc9,loc10}; 
qr_lookup(2,:) = {desc1,desc2,desc3,desc4,desc5,desc6,desc7,desc8,desc9,desc10};


% Clear everything except cell structure
clearvars -except qr_lookup            


% Write to .mat file
save('loc_data.mat','qr_lookup');



