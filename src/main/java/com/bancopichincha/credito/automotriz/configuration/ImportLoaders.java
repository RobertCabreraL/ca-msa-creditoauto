package com.bancopichincha.credito.automotriz.configuration;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportLoaders {

  private final BrandLoader brandLoader;
  private final CustomerLoader customerLoader;
  private final CarYardsLoader carYardsLoader;
  private final SaleExecutiveLoader saleExecutiveLoader;

  @PostConstruct
  public void executeImports() {
    brandLoader.loadBrandData();
    customerLoader.loadCustomerData();
    carYardsLoader.loadCarYardData();
    saleExecutiveLoader.loadSaleExecutiveData();
  }
}
